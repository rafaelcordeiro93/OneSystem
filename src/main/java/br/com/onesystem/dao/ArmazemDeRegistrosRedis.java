package br.com.onesystem.dao;

import br.com.onesystem.aop.ClassTypeAdapter;
import br.com.onesystem.exception.DadoInvalidoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static java.lang.System.currentTimeMillis;
import org.aspectj.lang.reflect.MethodSignature;
import redis.clients.jedis.Jedis;

public class ArmazemDeRegistrosRedis<T> {

    private Jedis jedis;
    private Gson gson;
    private Class<T> classe;

    public ArmazemDeRegistrosRedis(Class<T> classe) {
        this.classe = classe;
        jedis = new Jedis("127.0.0.1", 9900);
        gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassTypeAdapter()).create();
    }

    public Object listaTodosOsRegistros() {
        System.out.println("Teste 2");
        String json = jedis.get(gson.toJson(classe.toString()));

        Object o = null;
        if (json != null) {
            Envelope envelope = gson.fromJson(json, Envelope.class);

            o = gson.fromJson(envelope.json, classe);
            if (o != null && currentTimeMillis() <= envelope.expire) {
                System.out.println("&&& Recuperado pelo REDIS!");
                return o;
            }

        }

        try {
            Envelope envelope = new Envelope(gson.toJson(o), o.getClass(), currentTimeMillis() + (60 * 1000));

            jedis.setex(classe.toString(), 60 * 60 * 24 * 7, gson.toJson(envelope));
            return o;
        } catch (Exception e) {
            if (o != null) {
                System.out.println("&&& Recuperado pelo REDIS! Fonte não disponível!");
                return o;
            }
            throw e;
        }
    }

    public T find(Long id) {
        System.out.println("Teste Find 1");
        String json = jedis.get(gson.toJson(classe.toString()));

        Object o = null;
        if (json != null) {
            Envelope envelope = gson.fromJson(json, Envelope.class);

            o = gson.fromJson(envelope.json, classe);
            if (o != null && currentTimeMillis() <= envelope.expire) {
                System.out.println("&&& Recuperado pelo REDIS!");
                return (T) o;
            }

        }

        try {
            Envelope envelope = new Envelope(gson.toJson(o), o.getClass(), currentTimeMillis() + (60 * 1000));

            jedis.setex(classe.toString(), 60 * 60 * 24 * 7, gson.toJson(envelope));
            return (T) o;
        } catch (Exception e) {
            if (o != null) {
                System.out.println("&&& Recuperado pelo REDIS! Fonte não disponível!");
                return (T) o;
            }
            throw e;
        }
    }

}

class Envelope {

    String json;

    Class typeOfJson;

    long expire;

    public Envelope() {
    }

    public Envelope(String json, @SuppressWarnings("rawtypes") Class typeOfJson, long expire) {
        this.json = json;
        this.typeOfJson = typeOfJson;
        this.expire = expire;
    }

}
