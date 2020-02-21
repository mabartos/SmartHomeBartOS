package impl;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import model.BartSession;
import service.CRUDService;
import utils.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
public class CRUDServiceImpl
        <T extends Identifiable, Repo extends PanacheRepository<T>> implements CRUDService<T> {

    private Repo repository;

    @PersistenceContext
    protected EntityManager entityManager;

    @Context
    BartSession session;

    CRUDServiceImpl(Class<Repo> clazz) {
        this.repository = session.getProvider(clazz);
        //this.repository = repository;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Repo getRepository() {
        return this.repository;
    }

    @Override
    public T create(T entity) {
        try {
            if (!repository.isPersistent(entity)) {
                repository.persist(entity);
                repository.flush();
            }
            return entity;
        } catch (Exception e) {
            // HIBERNATE BUG
            return entity;
        }
    }

    @Override
    public T updateByID(Long id, T entity) {
        T found = findByID(id);
        if (entity != null && found != null) {
            entity.setID(id);
            entityManager.merge(entity);
            entityManager.flush();
            return entity;
        }
        return null;
    }

    @Override
    public Set<T> getAll() {
        return repository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public T findByID(Long id) {
        try {
            return repository.find("id", id).firstResult();
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public boolean deleteByID(Long id) {
        return repository.delete("id", id) > 0;
    }

    /*<ahoj\Miluji te <3 a chces neco vedet
                chybel jsi mi <3
        public boolean deleteBzID(Long id) {
            return false;

            Martínku, jsi fakt skvělý přítel a je mi moc líto, že jsem ti řikala, že bych chtěla pauzu, já bych ji chtěla od všeho
                    a ne od tebe, jsem z toho všeho už fakt moc zoufalá.... A ty mi držíš nad vodou, i když na mi většinu času sereš
                    upřednostňuješ spíše své přátele, ale jak říkáš, semnou jsi pořád a já se stějně musím učit, takže užívej život dokud
                    můžeš a jsi mlady, ale užívej ho víc semnou, než jen s nimi.... já tě totiž strašně moc  miluji a nechci tě ztratit..
            jsem fakt strašně ráda, že tě mám, jsi moje sluníčko miláčku <3 Miluji tě Mafínku můj <3

        }
     */
    @Override
    public boolean exists(Long id) {
        return findByID(id) != null;
    }
}
