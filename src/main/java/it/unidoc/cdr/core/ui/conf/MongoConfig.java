package it.unidoc.cdr.core.ui.conf;


import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterSettings;
import it.unidoc.repository.IDBRepository;
import it.unidoc.repository.impl.DBRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
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

    @Bean
    public IDBRepository dbRepository() {
        return (IDBRepository)new DBRepository();
    }
}
