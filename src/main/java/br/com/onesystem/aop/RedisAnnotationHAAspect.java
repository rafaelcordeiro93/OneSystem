package br.com.onesystem.aop;

import br.com.onesystem.exception.DadoInvalidoException;
import static java.lang.System.currentTimeMillis;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//@Aspect
public class RedisAnnotationHAAspect {

    private Jedis jedis;

    private Gson gson;

    public RedisAnnotationHAAspect() {
        System.out.println("Teste");
        jedis = new Jedis("127.0.0.1", 1200);
        gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassTypeAdapter()).create();
    }

    //  @Around("execution(* br.com.onesystem..*.*(..)) && @annotation(redisCached)")
    private Object around(ProceedingJoinPoint pjp, final RedisCached redisCached) throws Throwable {
        System.out.println("Teste 2");
        String json = jedis.get(pjp.getArgs()[0].toString());

        Object o = null;
        if (json != null) {
            Envelope envelope = gson.fromJson(json, Envelope.class);

            Class type = ((MethodSignature) pjp.getSignature()).getReturnType();

            o = gson.fromJson(envelope.json, type);
            if (o != null && currentTimeMillis() <= envelope.expire) {
                System.out.println("&&& Recuperado pelo REDIS!");
                return o;
            }

        }

        try {
            o = pjp.proceed();
        } catch (DadoInvalidoException e) {
            if (o != null) {
                System.out.println("&&& Recuperado pelo REDIS! Fonte não disponível!");
                return o;
            }
            throw e;
        }

        System.out.println("&&& Recuperado pelo BANCO! ");

        Envelope envelope = new Envelope(gson.toJson(o), o.getClass(), currentTimeMillis() + (redisCached.secondsToCache() * 1000));

        jedis.setex(pjp.getArgs()[0].toString(), 60 * 60 * 24 * 7, gson.toJson(envelope));

        return o;
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
