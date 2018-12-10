package com.ogm.demo.gql;


import com.ogm.demo.gql.node.Poc1FysiskProperty;
import com.ogm.demo.gql.node.Poc1NodeType;
import com.ogm.demo.gql.relationship.Married;
import com.ogm.demo.gql.node.Person;

//import com.sun.deploy.net.HttpResponse;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
//import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OGM Intro for loading data
 */
public class Loader {

    /**
     * Session factory for connecting to Neo4j database
     */
    private final SessionFactory sessionFactory;

    //  Configuration info for connecting to the Neo4J database
    static private final String SERVER_URI = "bolt://localhost:7687";
    static private final String SERVER_USERNAME = "neo4j";
    static private final String SERVER_PASSWORD = "wat#rMel0n";


    /**
     * Constructor
     */
    public Loader() {
        //  Define session factory for connecting to Neo4j database
        Configuration configuration = new Configuration.Builder().uri(SERVER_URI).credentials(SERVER_USERNAME, SERVER_PASSWORD).build();
        sessionFactory = new SessionFactory(configuration, "com.ogm.demo.gql.node", "com.ogm.demo.gql.relationship");
    }


    /**
     * Main method for starting Java program
     * @param args command line arguments
     */
    public static void main (String[] args) throws IOException {

        //  Create an instance of the class and process the file.
       new Loader().process();

       /** String data = "'{ \"query\": \"query { listPoc1Persons(limit:5) { items { partyKey } } }\" }'";
        URL url = new URL("https://lxe3l3aiefal7ekpgjy7byhdzi.appsync-api.us-east-1.amazonaws.com/graphql");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("x-api-key", "da2-u54bvpb5xjdtfktha4vhap6com");
        con.s
        con.setDoOutput(true);
        con.getOutputStream().write(data.getBytes("UTF-8"));
        //con.getInputStream();

        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();**/
        String payload = "'{ \"query\": \"query { listPoc1Persons(limit:5) { items { partyKey } } }\" }'";

        StringEntity entity = new StringEntity(payload,
                ContentType.WILDCARD);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://lxe3l3aiefal7ekpgjy7byhdzi.appsync-api.us-east-1.amazonaws.com/graphql");
        request.setHeader("x-api-key", "da2-u54bvpb5xjdtfktha4vhap6com");
        request.setEntity(entity);

         System.out.println(request.toString());
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.getStatusLine().getStatusCode());

        /** HttpClient httpclient = new DefaultHttpClient();
         HttpGet request = new HttpGet("https://lxe3l3aiefal7ekpgjy7byhdzi.appsync-api.us-east-1.amazonaws.com/graphql?");
         request.addHeader("x-api-key", "da2-u54bvpb5xjdtfktha4vhap6com");
         request.addHeader("query","{listPoc1Persons(limit:5){items{partyKey}}}");

         System.out.println(request);
         HttpResponse response = httpclient.execute(request);
         System.out.println(response);**/
    }

    /**
     * Method for doing work≥
     */
    private void process () {
        //  For demo purposes, create session and purge to cleanup whatever you have
        Session session = sessionFactory.openSession();
        // session.purgeDatabase();

        //  Load the data via OGM Need to bu run if no objects in the database

       // load (session);

        //  OGM Filter, querying by birth year
        /**System.out.println ("Querying nodes by single OGM filter");
        queryByFilter (1977, session).forEach (one -> System.out.println (one.getName() + " was born in " + one.getBirthYear()));

        //  OGM multi-part filter, querying by birth year or name greater than the given letter.
        System.out.println ("Querying nodes by multiple OGM filters.");
        queryByMultipleFilters(1977, "M", session).forEach (one -> System.out.println (one.getName() + " was born in " + one.getBirthYear()));
        **/
        //  OGM query using Cypher, finding those married to the name passed in.
        /**System.out.println ("Querying nodes using a Cypher statement.");
        queryByCypher("Michael Blevins", session).forEach (one -> System.out.println (one.getName() + " at some point was married to Michael Blevins"));

        System.out.println ("Querying nodes using a Cypher statement2.");
        queryByCypher( session).forEach (one -> System.out.println (one.getName() + " at some point w"));

        System.out.println ("Querying nodes using a Cypher statement3.");
        queryNodesByCypher( session).forEach (one -> System.out.println (one.getNamn() + " Poc1NodeType"));

        System.out.println ("Querying nodes using a Cypher statement4 .");
        queryPropertiesByCypher("Contact Method", session).forEach (one -> System.out.println (one.getNamn() + " For each node Poc1NodeType"));**/

        printGraphQL(session);
    }
    /**
     * Print GraphQL schema
     */

    private void printGraphQL(Session session)
    {
        // get relationships
        MultiValuedMap<String,String> map = new HashSetValuedHashMap<>();
        //MultiValuedMap<String,String> map2 = new HashSetValuedHashMap<>();

        queryRelationshipsByCypher(session).forEach(rel->map.put(rel.get("one").toString(),rel.get("many").toString()));
        String prefix="Poc2";
        System.out.println ("Printing GraphQL.");
        queryNodesByCypher(session).forEach (one -> {
           // System.out.println("type Poc1"+one.getNamn().replaceAll("\\s+","") + "   @model");
            System.out.println("type "+prefix+one.getNamn().replaceAll("\\s+","") + "   @model");
            System.out.println("{");
            System.out.println("");
            queryPropertiesByCypher(one.getNamn(),session)
                    .forEach(prop->{
                         System.out.println(prop.getNamn()
                        .substring(0,1)
                        .toLowerCase()+ prop.getNamn()
                        .replaceAll("\\s+","")
                        .replaceAll("\\.+","")
                                 .substring(1)+": String ")
                          ;
                         //+ "Acord data type "+prop.getDataType()

            });
            //get one to many function
            if(map.containsKey(one.getNamn()))
            {
                map.get(one.getNamn()).forEach(v->
                        //System.out.println("poc1"+v
                        System.out.println(v
                                .replaceAll("\\s+","")
                               // +"s: [Poc1"+v.replaceAll("\\s+","")+"]  @connection(name: "
                                +"s: ["+prefix+v.replaceAll("\\s+","")+"]  @connection(name:\""
                                +one.getNamn().replaceAll("\\s+","")+v.replaceAll("\\s+","")+"Connection\")"));
            }

           MultiValuedMap<String,String> map2=getManyToOne(map);
            // get many to one function
           if(map2.containsKey(one.getNamn()))
           {
               map2.get(one.getNamn()).forEach(v->
                       System.out.println(v
                               .substring(0,1)
                               .toLowerCase()+v
                               .replaceAll("\\s+","")
                               //.substring(1)+": Poc1"+v
                               .substring(1)+": "+prefix+v
                               .replaceAll("\\s+","")+" @connection(name:\"" +v.replaceAll("\\s+","")+one.getNamn().replaceAll("\\s+","")+"Connection\")"));
           }

            System.out.println("}");
        });

       /** queryRelationshipsByCypher(session).forEach(rel->System.out.println(rel.values().stream().anyMatch(one->one.equals("Person Name"))));
        List<String> collect = new ArrayList<>();
        queryRelationshipsByCypher(session).forEach(rel->collect.add(rel.toString()));
        System.out.println(collect.toString());
        //System.out.println("Relatiionships "+);
        queryRelationshipsByCypher(session).forEach(rel->System.out.println(rel.values()));
        HashMap kv = new HashMap();
        queryRelationshipsByCypher(session).forEach(rel->kv.put(rel.get("one"),rel.get("many")));
        //MultiMap<String,String> map = new MultiValueMap<>();
        //MultiValuedMap<String,String> map = new HashSetValuedHashMap<>();
        //queryRelationshipsByCypher(session).forEach(rel->map.put(rel.get("one").toString(),rel.get("many").toString()));
        System.out.println("HashMap "+ kv.toString());
        System.out.println("HashMap "+ kv.get("one")+kv.get("many"));**/
       // System.out.println("MAP "+map.toString());
        //getManyToOne(map);
        //System.out.println("&&&&&"+getManyToOne(map).toString());



    }

    /**
     * Load the data.
     */
    private void load (Session session) {

        //  All work done in single transaction.
        Transaction txn = session.beginTransaction();

        //  Create all persons.
        Person Carol = new Person ("Carol Maureen", 1945);
        Person Courtney = new Person ("Courtney Janice", 1945);
        Person Esme = new Person ("Esme Alexis", 1981);
        Person Gabe = new Person ("Gabriel Josiah", 1979);
        Person Gail = new Person ("Gail Ann", 1942);
        Person Jeremy = new Person ("Jeremy Douglas", 1969);
        Person Jesse = new Person ("Jesse Lucas", 1977);
        Person Kelly = new Person ("Kelly Leigh", 1977);
        Person Mike = new Person ("Michael Blevins", 1945);
        Person Scott = new Person ("Scott Christoper", 1965);
        Person Steve = new Person ("Steven Lester", 1950);
        Person Zane = new Person ("Michael Zane", 1973);

        //  Add children to each parent.
        List<Person> children = Carol.getChildren();
        children.add (Scott);
        children.add (Courtney);
        children.add (Jeremy);
        children.add (Jesse);
        children.add (Gabe);
        children.add (Esme);
        children = Mike.getChildren();
        children.add (Scott);
        children.add (Courtney);
        children.add (Jeremy);
        children.add (Zane);
        children.add (Kelly);
        children = Gail.getChildren();
        children.add (Zane);
        children.add (Kelly);
        children = Steve.getChildren();
        children.add (Jesse);
        children.add (Gabe);
        children.add (Esme);


        //  Save to database
        session.save (Carol);
        session.save (Courtney);
        session.save (Esme);
        session.save (Gabe);
        session.save (Gail);
        session.save (Jeremy);
        session.save (Jesse);
        session.save (Kelly);
        session.save (Mike);
        session.save (Scott);
        session.save (Steve);
        session.save (Zane);

        //  Create all marriages and save to database
        session.save (new Married(Carol, Mike, 1964, 1973));
        session.save (new Married(Gail, Mike, 1973, 1992));
        session.save (new Married(Carol, Steve, 1976, null));

        //  Commit the transaction.
        txn.commit();
    }

    /**
     * Example of querying using an OGM filter.
     * @param birthYear a person's birth year
     * @param session Neo4J session
     * @return collection of zero or more persons returned by the filter
     */
    private Iterable<Person> queryByFilter (int birthYear,
                                            Session session) {

        //  Create an OGM filter for the birthYear property.
        Filter filter = new Filter ("birthYear", ComparisonOperator.EQUALS, birthYear);

        //  Load all Persons with the given birth year.
        return session.loadAll (Person.class, filter);
    }

    /**
     * Create a composite filter for querying Neo4J via OGM
     * @param birthYear a person's birth year
     * @param startingLetter the letter for which a person's name is greater than
     * @param session Neo4J session
     * @return collection of zero or more persons returned by the filters
     */
    private Iterable<Person> queryByMultipleFilters (int birthYear,
                                                     String startingLetter,
                                                     Session session) {

        //  Filter either by the birth year or name greater than the starting letter
        Filters composite = new Filters();
        Filter filter = new Filter ("birthYear", ComparisonOperator.EQUALS, birthYear);
        composite.add(filter);
        filter = new Filter ("name", ComparisonOperator.GREATER_THAN, startingLetter);
        filter.setBooleanOperator(BooleanOperator.OR);
        composite.add(filter);

        //  Load all Persons which match the composite filter.
        return session.loadAll (Person.class, composite);
    }

    /**
     * Query Neo4J by providing a Cypher statement and parameters to plug in
     * @param marriedTo the destination node of the MARRIED relationship
     * @param session the Neo4J session
     * @return who's married to the name specified
     */
    private Iterable<Person> queryByCypher (String marriedTo,
                                            Session session) {

        //  Create/load a map to hold the parameter
        Map<String, Object> params = new HashMap<>(1);
        params.put ("name", marriedTo);

        //  Execute query and return the other side of the married relationship
        String cypher = "MATCH (w:Person)-[:MARRIED]->(h:Person {name:$name}) RETURN w";
        return session.query (Person.class, cypher, params);
    }
    private Iterable<Person> queryByCypher (Session session) {

        //  Create/load a map to hold the parameter
        Map<String, Object> params = new HashMap<>(1);
        //params.put ("name", marriedTo);

        //  Execute query and return the other side of the married relationship
        String cypher = "MATCH (w:Person) RETURN w";
        return session.query (Person.class, cypher, params);
    }

    private Iterable<Poc1NodeType> queryNodesByCypher (Session session) {

        //  Create/load a map to hold the parameter
        Map<String, Object> params = new HashMap<>(1);
        //params.put ("name", marriedTo);

        //  Execute query and return the other side of the married relationship
        String cypher = "MATCH (w:Poc1NodeType) where w.Namn <> 'Information Model Object' RETURN w";
        return session.query (Poc1NodeType.class, cypher, params);
    }

    private Iterable<Poc1FysiskProperty> queryPropertiesByCypher (String nodeName, Session session) {

        //  Create/load a map to hold the parameter
        Map<String, Object> params = new HashMap<>(1);
        params.put ("Namn", nodeName);
        //
        params.put("dataType",nodeName);

        //  Execute query and return the other side of the married relationship
        String cypher = "match(n:Poc1NodeType{Namn:$Namn})-[:POC1_HAR_PROPERTY]->(m:Poc1FysiskProperty) return m;";
        return session.query (Poc1FysiskProperty.class, cypher, params);
    }
    private Iterable<Map<String,Object>> queryRelationshipsByCypher(Session session) {

        //String cypher = "match(n:Poc1NodeType)-[r:POC1_HAR_RELATIONSHIP]->(rel:Poc1FysiskRelation)-[r2:POC1_HAR_RELATIONSHIP]->(m:Poc1NodeType) return n.Namn, collect ([m.Namn,rel.cardinality]);";
       String cypher = "match(n:Poc1NodeType)-[r:POC1_HAR_RELATIONSHIP]->(rel:Poc1FysiskRelation)-[r2:POC1_HAR_RELATIONSHIP]->(m:Poc1NodeType) where rel.cardinality=\"*\"  and n.Namn<>'Information Model Object' return n.Namn as one,m.Namn as many;";
        return session.query(cypher, Collections.EMPTY_MAP);
    }

    /**
     * Get other side of relationships
     * @param map conatining many to one relationships
     * @return one to many relationsips
     */
    private MultiValuedMap getManyToOne (MultiValuedMap map)
    {
        MapIterator<String,String> it = map.mapIterator();
        MultiValuedMap map1 = new HashSetValuedHashMap();

        while(it.hasNext())
        {
            String one = it.next();
            String many = it.getValue();
            //System.out.println("#####"+it.next());
            map1.put(many,one);
            //System.out.println("%%%%%%"+it.getValue());

        }

        return map1;
    }
}
