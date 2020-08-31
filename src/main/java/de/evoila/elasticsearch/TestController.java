package de.evoila.elasticsearch;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.init.Jackson2ResourceReader;

import java.util.Collection;

public class TestController implements ApplicationListener<ApplicationReadyEvent> {
    private final Jackson2ResourceReader resourceReader;
    private final Resource sourceData;

    public TestController() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        resourceReader = new Jackson2ResourceReader(mapper);
        sourceData = new ClassPathResource("article.json", this.getClass().getClassLoader());
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        CrudRepository articleRepository;
        articleRepository =
                BeanFactoryUtils.beanOfTypeIncludingAncestors(event.getApplicationContext(), CrudRepository.class);
        if (articleRepository != null && articleRepository.count() == 0) {
            populate(articleRepository);
        }
    }

    private void populate (CrudRepository repository){
        Object entity = getEntityFromResource(sourceData);

        if (entity instanceof Collection) {
            for (Article article : (Collection<Article>) entity) {
                if (article != null) {
                    repository.save(article);
                }
            }
        } else {
            repository.save(entity);
        }
    }

    private Object getEntityFromResource (Resource resource){
        try {
            return resourceReader.readFrom(resource, this.getClass().getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
