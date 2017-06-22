
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael Fernando Rauber
 */
public class RedisTeste {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1", 1200);

        jedis.set("usuario:61", "rafael cordeiro");

        jedis.get("usuario:61");
    }

}
