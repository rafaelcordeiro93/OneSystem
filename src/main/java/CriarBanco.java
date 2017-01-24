

import br.com.onesystem.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CriarBanco {

    public static void main(String[] args) {

        try {

            EntityManager manager = JPAUtil.getEntityManager();

            String consulta = "select r from Recepcao r";

            Query query = manager.createQuery(consulta);

            query.getResultList();
        } catch (Exception die) {
            System.out.println(die.getMessage());
        }
    }

}
