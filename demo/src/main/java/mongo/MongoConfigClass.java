package mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;

 
//@EnableMongoRepositories
@Configuration
public class MongoConfigClass {
	
	@Value("{spring.data.mongodb.userName}")
	private String userName;
	@Value("{spring.data.mongodb.database}")
	private String database;
	@Value("{spring.data.mongodb.password}")
	private String password;
	

//	@Override
//	public MongoClient mongoClient() {
//		// TODO Auto-generated method stub
//			MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
//			return new MongoClient(new ServerAddress("localhost", 27017) , Arrays.asList(credential));
//
//	}
//
//	@Override
//	protected String getDatabaseName() {
//		// TODO Auto-generated method stub
//		return database;
//	}
	
	@Bean
	public MongoTemplate mongoTemplate() throws Exception
	{
		 EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
	        mongo.setBindIp("localhost");
	        MongoClient mongoClient = mongo.getObject();
		return new MongoTemplate(mongoClient, database);
	}
}
