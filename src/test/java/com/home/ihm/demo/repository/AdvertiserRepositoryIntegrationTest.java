package com.home.ihm.demo.repository;

import com.home.ihm.demo.domain.Advertiser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = AdvertiserRepository.class)
@EntityScan(basePackageClasses = Advertiser.class)
// sanity check for the generated SpringData methods we use and actual custom one
public class AdvertiserRepositoryIntegrationTest {

    @Autowired
    AdvertiserRepository dao;

    @Test
    public void save() {
        // given
        Advertiser advertiser = createAdvertiser();

        // when
        assertThat(advertiser.getId() == null);

        // then
        Advertiser persisted = dao.save(advertiser);
        assertThat(persisted.getId() != null);
        assertThat(persisted.getName().equals(advertiser.getName()));
    }

    @Test
    public void findOne() {
        // given
        Advertiser advertiser = createAdvertiser();
        dao.save(advertiser);

        // when
        assertThat(advertiser.getId() != null);

        // then
        Advertiser found = dao.getOne(advertiser.getId());
        assertThat(found.getId().longValue() == advertiser.getId().longValue());
        assertThat(found.getName().equals(advertiser.getName()));
    }

    @Test
    public void update() {
        // given
        Advertiser advertiser = createAdvertiser();
        dao.save(advertiser);

        // when
        String newAdvertiserName = "New Advertiser Name";//randomAlphanumeric(5);
        advertiser.setName(newAdvertiserName);
        dao.save(advertiser);

        // then
        Advertiser updated = dao.getOne(advertiser.getId());
        assertThat(updated.getId().longValue() == advertiser.getId().longValue());
        assertThat(updated.getName().equals(newAdvertiserName));
        assertThat(!updated.getContactName().equals(advertiser.getContactName()));
        assertThat(!updated.getCreditLimt().equals(advertiser.getCreditLimt()));
    }

    @Test(expected = JpaObjectRetrievalFailureException.class)
    public void delete() {
        // given
        Advertiser advertiser = createAdvertiser();
        dao.save(advertiser);

        // when
        dao.delete(advertiser);

        // then
        Advertiser notFound = dao.getOne(advertiser.getId());
    }

    public void deleteOption() {
        // given
        Advertiser advertiser = createAdvertiser();
        dao.save(advertiser);

        // when
        dao.delete(advertiser);

        // then
        Optional<Advertiser> notFound = dao.findById(advertiser.getId());
        assertThat( !notFound.isPresent() );
    }


    private Advertiser createAdvertiser() {
        Advertiser advertiser = new Advertiser();
        //Advertiser.setId(RandomUtils.nextLong()); // note exists after persisted only
        advertiser.setName("Advertiser A");
        advertiser.setContactName("Don K");
        advertiser.setCreditLimt(5000L/*Random.nextLong()*/);
        return advertiser;
    }

}
