

package it.unidoc.repository.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ClusterSettings;
import com.vaadin.flow.internal.Pair;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.BaseFse2;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.CdrFse2Validation;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.MetadataFse2;
import it.unidoc.repository.IDBRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class DBRepository implements IDBRepository {
    @Autowired
    private Environment env;

    protected String getDatabaseName() {
        return this.env.getProperty("spring.data.mongodb.database");
    }

    public MongoClient mongoClient() {
        String host = this.env.getProperty("spring.data.mongodb.host");
        int port = ((Integer)this.env.getProperty("spring.data.mongodb.port", Integer.class)).intValue();
        String username = this.env.getProperty("spring.data.mongodb.username");
        String password = this.env.getProperty("spring.data.mongodb.password");
        String authdb = this.env.getProperty("spring.data.mongodb.authentication-database");
        MongoClientSettings settings = MongoClientSettings.builder().applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress[] { new ServerAddress(host, port) }))).credential(MongoCredential.createCredential(username, authdb, password.toCharArray())).build();
        return MongoClients.create(settings);
    }

    private String getField(Document document, String field) {
        return ((Document)document.get(field)).toJson();
    }

    private Date toEndDate(Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);

        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(14, 999);

        return calendar.getTime();
    }

    public List<BaseFse2> getBaseItems(String collectionName, Pair<String, String> pair, int statusCode) throws Exception {
        MongoDatabase database = mongoClient().getDatabase(getDatabaseName());
        MongoCollection<Document> collection = database.getCollection(collectionName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date startDate = dateFormat.parse((String)pair.getFirst());
        Date endDate = toEndDate(dateFormat.parse((String)pair.getSecond()));
        Document query = new Document();
        query.append("created", (new Document("$gte", startDate)).append("$lte", endDate));
        List<BaseFse2> documents = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find((Bson)query).iterator();
        try {
            while (cursor.hasNext()) {
                Document document = (Document)cursor.next();
                BaseFse2 c = new BaseFse2();
                c.set_id(document.get("_id").toString());
                c.setCreated(document.getDate("created"));
                c.setRequested(document.getDate("requested"));
                c.setSent(document.getDate("sent"));
                c.setBearerToken(document.getString("bearerToken"));
                c.setClaimsToken(document.getString("claimsToken"));
                c.setServiceUrl(document.getString("serviceUrl"));
                c.setResponse(getField(document, "response"));
                c.set_class(document.getString("_class"));
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(c.getResponse());
                    c.setStatusCode(jsonNode.get("statusCode").asInt());
                } catch (Exception exception) {}
                documents.add(c);
            }
            if (cursor != null)
                cursor.close();
        } catch (Throwable throwable) {
            if (cursor != null)
                try {
                    cursor.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
            throw throwable;
        }
        return statusFilter(statusCode, documents);
    }

    public List<CdrFse2Validation> getValidationItems(String collectionName, Pair<String, String> pair, int statusCode) throws Exception {
        MongoDatabase database = mongoClient().getDatabase(getDatabaseName());
        MongoCollection<Document> collection = database.getCollection(collectionName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date startDate = dateFormat.parse((String)pair.getFirst());
        Date endDate = toEndDate(dateFormat.parse((String)pair.getSecond()));
        Document query = new Document();
        query.append("created", (new Document("$gte", startDate)).append("$lte", endDate));
        List<CdrFse2Validation> documents = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find((Bson)query).iterator();
        try {
            while (cursor.hasNext()) {
                Document document = (Document)cursor.next();
                CdrFse2Validation c = new CdrFse2Validation();
                c.set_id(c.get_id());
                c.setBody(getField(document, "body"));
                if (!collectionName.equals("cdr_fse2_metadata")) {
                    c.setFileName(document.getString("filename"));
                    c.setPatient(getField(document, "patient"));
                    c.setAuthor(getField(document, "author"));
                    c.setStructureId(document.getString("structureId"));
                    c.setCustodian(getField(document, "custodian"));
                    c.setAuthenticator(getField(document, "authenticator"));
                }
                c.setCreated(document.getDate("created"));
                c.setRequested(document.getDate("requested"));
                c.setSent(document.getDate("sent"));
                c.setBearerToken(document.getString("bearerToken"));
                c.setClaimsToken(document.getString("claimsToken"));
                c.setServiceUrl(document.getString("serviceUrl"));
                c.setResponse(getField(document, "response"));
                c.set_class(document.getString("_class"));
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(c.getResponse());
                    c.setStatusCode(jsonNode.get("statusCode").asInt());
                } catch (Exception exception) {}
                documents.add(c);
            }
            if (cursor != null)
                cursor.close();
        } catch (Throwable throwable) {
            if (cursor != null)
                try {
                    cursor.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
            throw throwable;
        }
        return statusFilter(statusCode, documents);
    }

    private <T extends BaseFse2> List<T> statusFilter(int statusCode, List<T> documents) {
        if (statusCode == 0)
            return documents;
        if (statusCode == 1)
            return (List<T>)documents.stream().filter(d -> (d.getStatusCode() == 200 || d.getStatusCode() == 201)).collect(Collectors.toList());
        if (statusCode == -1)
            return (List<T>)documents.stream().filter(d -> (d.getStatusCode() != 200 && d.getStatusCode() != 201)).collect(Collectors.toList());
        return (List<T>)documents.stream().filter(d -> (d.getStatusCode() == statusCode)).collect(Collectors.toList());
    }

    public List<MetadataFse2> getMetadataItems(String collectionName, Pair<String, String> pair, int statusCode) throws Exception {
        MongoDatabase database = mongoClient().getDatabase(getDatabaseName());
        MongoCollection<Document> collection = database.getCollection(collectionName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date startDate = dateFormat.parse((String)pair.getFirst());
        Date endDate = toEndDate(dateFormat.parse((String)pair.getSecond()));
        Document query = new Document();
        query.append("created", (new Document("$gte", startDate)).append("$lte", endDate));
        List<MetadataFse2> documents = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find((Bson)query).iterator();
        try {
            while (cursor.hasNext()) {
                Document document = (Document)cursor.next();
                MetadataFse2 c = new MetadataFse2();
                c.set_id(c.get_id());
                c.setBody(getField(document, "body"));
                c.setCreated(document.getDate("created"));
                c.setRequested(document.getDate("requested"));
                c.setSent(document.getDate("sent"));
                c.setBearerToken(document.getString("bearerToken"));
                c.setClaimsToken(document.getString("claimsToken"));
                c.setServiceUrl(document.getString("serviceUrl"));
                c.setResponse(getField(document, "response"));
                c.set_class(document.getString("_class"));
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(c.getResponse());
                    c.setStatusCode(jsonNode.get("statusCode").asInt());
                } catch (Exception exception) {}
                documents.add(c);
            }
            if (cursor != null)
                cursor.close();
        } catch (Throwable throwable) {
            if (cursor != null)
                try {
                    cursor.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
            throw throwable;
        }
        return statusFilter(statusCode, documents);
    }
}
