package org.naebulae.access;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;
import org.naebulae.util.String2;
import org.naebulae.util.func.JsacAction;
import org.naebulae.util.func.JsacAction2;
import org.naebulae.util.func.JsacMapper;
import org.naebulae.util.func.JsacPredicate;
import org.naebulae.util.japi.JapiName;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import apps.trenzi.physical.TrenziDataAccess;

public class MongoAccess1970 
{
	public String dataName = "";
	public String dataHost = "localhost";
	public int dataPort = 27017;
	
	public static MongoAccess1970 start(String name) 
	{
		TrenziDataAccess res = new TrenziDataAccess();
		res.dataName = name;
		return res;
	}
	
	public void copyTablesTo(MongoAccess1970 db) 
	throws Exception
	{
		System.out.println("Copying tables from ["+this.dataName+"] to ["+db.dataName+"]");
		
		for(String tk: this.getTableNames())
		{
			List<Document> rows = this.fetchRowsAsDocuments(tk);
			db.dropAndInsertDocuments(tk, rows);
		}
		
		return;
	}
	
	
	
	public String toJsonString(Object src) 
	throws Exception
	{
		return (new ObjectMapper()).writeValueAsString(src);
	}	

	
	public void dropAllTables() 
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		
		MongoDatabase db = mongo.getDatabase(dataName);
		db.drop();
		
		mongo.close();
	}

	
	public<VAL1, KEY1> Map<KEY1, VAL1> indexRows(Class<VAL1> cl, JsacMapper<VAL1, KEY1> lf) 
	throws Exception
	{
		String tname = this.tableNameFromClass(cl);
		return indexRows(tname, cl, lf);
	}
	
	public<VAL1, KEY1> Map<KEY1, VAL1> indexRows(String tname, Class<VAL1> cl, JsacMapper<VAL1, KEY1> lf) 
	throws Exception
	{
		Map<KEY1, VAL1> res = new TreeMap<KEY1, VAL1>();
		
		for(VAL1 ik: this.fetchRows("tab-course", cl))
		{
			res.put(lf.invokeJsacMapper(ik), ik);
		}
		
		return res;
	}
	
	public<VAL1, KEY1> Map<KEY1, List<VAL1>> indexMapList(String tname, Class<VAL1> cl, JsacMapper<VAL1, KEY1> lf) 
	throws Exception
	{
		Map<KEY1, List<VAL1>> res = new TreeMap<KEY1, List<VAL1>>();
		
		for(VAL1 ik: this.fetchRows("tab-course", cl))
		{
			KEY1 k = lf.invokeJsacMapper(ik);
			
			List<VAL1> lk = res.get(k);
			if(lk == null) res.put(k, lk = new ArrayList<VAL1>());
			
			lk.add(ik);
		}
		
		return res;
	}	
	
	public<VAL1, KEY1> Map<KEY1, Set<VAL1>> indexMapSet(String tname, Class<VAL1> cl, JsacMapper<VAL1, KEY1> lf) 
	throws Exception
	{
		Map<KEY1, Set<VAL1>> res = new TreeMap<KEY1, Set<VAL1>>();
		
		for(VAL1 ik: this.fetchRows("tab-course", cl))
		{
			KEY1 k = lf.invokeJsacMapper(ik);
			
			Set<VAL1> lk = res.get(k);
			if(lk == null) res.put(k, lk = new TreeSet<VAL1>());
			
			lk.add(ik);
		}
		
		return res;
	}
	
	public<T1> void delete(Class<T1> cl, JsacPredicate<T1> lf)
	throws Exception
	{
		String tname = this.tableNameFromClass(cl);
		delete(tname, cl, lf);
	}
	
	public<T1> void delete(String tname, Class<T1> cl, JsacPredicate<T1> lf)
	throws Exception
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		MongoCollection<Document> table = db.getCollection(tname);
		
		List<Document> res = new ArrayList<Document>();
		
		for(Document tk: table.find())
		{
			T1 rk = cl.newInstance();
			copyFromDocToObject(tk, rk, cl);
			if(! lf.invokeJsacFilter(rk) ) res.add(tk);
		}
		
		table.drop();
		if( res.size() > 0 ) table.insertMany(res);
		
		mongo.close();					
	}	
	
	public List<String> getTableNames()
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);

		List<String> res = new ArrayList<String>();
		for(String tk: db.listCollectionNames())
		{
			res.add(tk);
		}
			
		mongo.close();					
		
		Collections.sort(res);
		return res;
	}
	
	public Set<String> getFieldNamesForTable(String tname) 
	{
		Set<String> res = new LinkedHashSet<String>();
		
		for(Object ik: this.fetchRowsFromTable(tname))
		{
			Document dk = (Document)ik;
			res.addAll(dk.keySet());
		}
		
		return res;
	}
	

	public boolean containsTable(String tname) 
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);

		boolean res = false;
		for(String tk: db.listCollectionNames())
		{
			if(tk.equals(tname)) { res = true; break; }
		}
		
		mongo.close();					

		return res;
	}

	public int countRows(Class<?> cl) 
	{
		return countRows(this.tableNameFromClass(cl));
	}
	
	public int countFields(String tk) 
	{
		return this.getFieldNamesForTable(tk).size();
	}
	
	public int countRows(String tname) 
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		int res = (int)db.getCollection(tname).count();
		mongo.close();					

		return res;		
	}
	
	public List<Object> fetchRowsFromTable(String tname) 
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);

		List<Object> res = new ArrayList<Object>();
		
		for(Object tk: db.getCollection(tname).find())
		{
			res.add(tk);
		}
			
		mongo.close();					

		return res;		
	}

	public List<Document> fetchRowsAsDocuments(String tname) 
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);

		List<Document> res = new ArrayList<Document>();
		for(Document tk: db.getCollection(tname).find()) res.add(tk);
		
		mongo.close();					

		return res;		
	}

	public void dropTable(String tname) 
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		MongoCollection<Document> table = db.getCollection(tname);
		table.drop();
		
		mongo.close();							
	}

	
	public void dropAndInsert(String tname, List<Object> items) 
	throws Exception
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		MongoCollection<Document> table = db.getCollection(tname);
		table.drop();
		
		table.insertMany(items.stream()
				.map(x -> convertMapToDoc(x))
				.collect(Collectors.toList()) );
		
		mongo.close();					
	}

	public void dropAndInsertDocuments(String tname, List<Document> items) 
	throws Exception
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		MongoCollection<Document> table = db.getCollection(tname);
		table.drop();
		table.insertMany(items);
		
		mongo.close();					
	}

	
	public void dropAndInsert(String tname, File f1) 
	throws Exception
	{	
		List<Object> rows = this.fetchRowsFromJson(f1);
		dropAndInsert(tname, rows);
	}


	@SuppressWarnings("unchecked")
	public List<Object> fetchRowsFromJson(File f1)
	throws Exception
	{
		ObjectMapper json = new ObjectMapper();
		return json.readValue(f1, List.class); 
	}

	public<T1> List<T1> fetchRows(Class<T1> cl) 
	throws Exception
	{
		String tname = this.tableNameFromClass(cl);
		return fetchRows(tname, cl);
	}
	
	public<T1> List<T1> fetchRows(String tname, Class<T1> cl) 
	throws Exception
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		List<T1> res = new ArrayList<T1>();
		
			for(Document tk: db.getCollection(tname).find())
			{
				T1 rk = cl.newInstance();
				copyFromDocToObject(tk, rk, cl);
				res.add(rk);
			}
		
		mongo.close();					
		
		return res;
	}
	
	public<T1> List<T1> fetchRows(Class<T1> cl, JsacPredicate<T1> lf) 
	throws Exception
	{
		String tname = this.tableNameFromClass(cl);
		return fetchRows(tname, cl, lf);
	}
	
	public<T1> List<T1> fetchRows(String tname, Class<T1> cl, JsacPredicate<T1> lf) 
	throws Exception
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		List<T1> res = new ArrayList<T1>();
		
		for(Document tk: db.getCollection(tname).find())
		{
			T1 rk = cl.newInstance();
			copyFromDocToObject(tk, rk, cl);
			if(lf.invokeJsacFilter(rk)) res.add(rk);
		}
		
		mongo.close();					
		
		return res;
	}	
	
	
	@SuppressWarnings("unchecked")
	public Document convertMapToDoc(Object ik) 
	{
		Map<String, Object> src = (Map<String, Object>)ik;
		
		Document res = new Document();
		for(String nk: src.keySet()) res.put(nk, src.get(nk));
		return res;
	}	

	public<T1> void copyFromDocToObject(Document tk, T1 rk, Class<T1> cl) 
	{
		for(String nk: tk.keySet())
		try	
		{
			Field fk = cl.getField(nk);
			fk.set(rk, tk.get(nk));
		}
		catch(Exception xp) {  }
		
		return;
	}

	public<T1> List<T1> select(List<T1> items,  JsacPredicate<T1> lf) 
	{
		List<T1> res = new ArrayList<T1>();
		for(T1 ik: items) if(lf.invokeJsacFilter(ik)) res.add(ik);
		return res;
	}
	
	public void insertColumn(String tname, JsacMapper<Document, Document> lf) 
	throws Exception
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		MongoCollection<Document> table = db.getCollection(tname);
		
		List<Document> items = new ArrayList<Document>();
		
		for(Document dk: table.find())
		{
			Document tk = lf.invokeJsacMapper(dk);
			items.add(tk);
		}
		
		table.drop();
		table.insertMany(items);
		
		mongo.close();							
	}
	
	public void updateRows(Class<?> cl, JsacMapper<Document, Document> lf) 
	throws Exception
	{
		String tname = this.tableNameFromClass(cl);
		updateRows(tname, lf);
	}
	
	public void updateRows(String tname, JsacMapper<Document, Document> lf) 
	throws Exception
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		MongoCollection<Document> table = db.getCollection(tname);
		
		List<Document> items = new ArrayList<Document>();
		
		for(Document dk: table.find())
		{
			Document tk = lf.invokeJsacMapper(dk);
			items.add(tk);
		}
		
		table.drop();
		table.insertMany(items);
		
		mongo.close();							
	}
	
	public<T1> void insert(Class<T1> cl, T1 src) 
	throws Exception
	{
		String tname = this.tableNameFromClass(cl);
		insert(tname, src);
	}
	
	public<T1> void insert(Class<T1> cl, JsacAction<T1> lf) 
	throws Exception
	{
		String tname = this.tableNameFromClass(cl);
		T1 src = cl.newInstance();
		lf.invokeJsacAction(src);
		insert(tname, src);
	}
	
	public void insert(String tname, Object src) 
	throws Exception
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		MongoCollection<Document> table = db.getCollection(tname);
		
		table.insertOne( convertObjectToDoc(src) );
		
		mongo.close();							
	}
	
	public<T1> T1 insertWithCheck(Class<T1> cl, T1 src, JsacPredicate<T1> lf) 
	throws Exception
	{
		String tname = this.tableNameFromClass(cl);
		return insertWithCheck(tname, src, cl, lf);		
	}
	
	public String tableNameFromClass(Class<?> cl) 
	{
		JapiName r = cl.getAnnotation(JapiName.class);
		return r==null ? null : r.value();
	}
	

	public<T1> T1 insertWithCheck(String tname, T1 src, Class<T1> cl, JsacPredicate<T1> lf) 
	throws Exception
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		MongoCollection<Document> table = db.getCollection(tname);
		
		T1 found = null;
		
		for(Document tk: table.find())
		{
			T1 rk = cl.newInstance();
			copyFromDocToObject(tk, rk, cl);
			if(lf.invokeJsacFilter(rk)) { found = rk; break; } 
		}
		
		if(found == null)
		{
			table.insertOne( convertObjectToDoc(src) );
			found = findOne(tname, cl, lf);
		}
		
		mongo.close();
		
		return found;
	}
	
	public<T1> T1 findOne(Class<T1> cl, JsacPredicate<T1> lf) 
	throws Exception
	{
		String tname = this.tableNameFromClass(cl);
		return findOne(tname, cl, lf);
	}
	
	public<T1> T1 findOne(String tname, Class<T1> cl, JsacPredicate<T1> lf) 
	throws Exception
	{
		MongoClient mongo = new MongoClient(dataHost, dataPort);
		MongoDatabase db = mongo.getDatabase(dataName);
		
		MongoCollection<Document> table = db.getCollection(tname);
		
		T1 found = null;
		
		for(Document tk: table.find())
		{
			T1 rk = cl.newInstance();
			copyFromDocToObject(tk, rk, cl);
			if(lf.invokeJsacFilter(rk)) { found = rk; break; } 
		}
		
		mongo.close();
		
		return found;
	}



	public Document convertObjectToDoc(Object src) 
	throws Exception
	{
		Document res = new Document();
		
		for(Field fk: src.getClass().getDeclaredFields())
		if( Modifier.isPublic(fk.getModifiers()) )
		{
			Object vk = fk.get(src);
			res.put(fk.getName(), vk);
		}
		
		return res;
	}


	public<T1, T2> void crossProduct(List<T1> left, List<T2> right, JsacAction2<T1, T2> lf) 
	throws Exception
	{
		for(T1 lk: left)
		for(T2 rj: right)
		lf.invokeJsacAction(lk, rj);
	}

	@SuppressWarnings("unchecked")
	public<T1, T2> void crossProductMap(List<T1> left, List<T2> right, 
			JsacAction2<Map<String, Object>, Map<String, Object>> lf) 
	throws Exception
	{
		for(T1 lk: left)
		for(T2 rj: right)
		lf.invokeJsacAction((Map<String, Object>)lk, (Map<String, Object>)rj);
	}

	public void exportRowsToJson(List<Object> rows, File file)
	throws Exception
	{
		ObjectMapper json = new ObjectMapper();
		
		file.getParentFile().mkdirs();
		json.writeValue(file, rows); 
	}
	
	public void exportDataObjects(File f, boolean showFields, boolean showRows) 
	throws Exception
	{
		PrintWriter out = new PrintWriter(f);
		
		out.println("@db: " + this.dataName);
		
		for(String tk: this.getTableNames())
		{
			out.println("");
			out.println("--------------------------");
			out.println("@table: " + tk + ": rows=" + this.countRows(tk) + ": fields=" + this.countFields(tk));
			
			if(showFields)
			for(String dj: this.getFieldNamesForTable(tk) )
				out.println("@table.field: " + dj);
			
			if(showRows)
			for(Object dj: this.fetchRowsFromTable(tk) )
				out.println("@table.row: " + String2.identifier256(dj.toString()) );
		}
		
		out.close();
	}

	@SuppressWarnings("unchecked")
	public void extend(List<Object> left, List<Object> right, 
			String leftKey, String rightKey,
			JsacAction2<Map<String, Object>, List<Object>> lf)
	throws Exception
	{
		Map<Object, List<Object> > lut = indexMapObjects(right, rightKey);
		
		for(Object _lk: left)
		{
			Map<String, Object> lk = (Map<String, Object>) _lk;
			
			Object vk = lk.get(leftKey);
			List<Object> rj = lut.get(vk); 
			lf.invokeJsacAction(lk, rj);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<Object, List<Object>> indexMapObjects(List<Object> items, String rightKey) 
	{
		Map<Object, List<Object>>  res = new TreeMap<Object, List<Object>>();
		
		for(Object _ik: items)
		{
			Map<String, Object> ik = (Map<String, Object>) _ik;
			Object vk = ik.get(rightKey);
			
			List<Object> lk = res.get(vk);
			
			if(lk==null) res.put(vk, lk = new ArrayList<Object>());
			
			lk.add(_ik);
		}
		
		return res;		
	}

}
