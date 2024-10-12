package com.ericmignardi.gms;

import com.ericmignardi.gms.model.Guitar;
import com.ericmignardi.gms.repository.GuitarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class GuitarRepositoryTests {

    @Autowired
    private GuitarRepository guitarRepository;

    @Test
    public void testCreate() {
        Guitar guitar = new Guitar();
        guitar.setBrand("Fender");
        guitar.setModel("Starcaster");
        guitar.setColour("Olympic White");
        guitar.setType("Electric");
        guitar.setSerialNumber("asdf23asdfdfas");
        guitar.setDescription("This is a custom shop guitar.");
        guitar.setFileName("starcaster_guitar.jpg");
        Guitar savedGuitar = guitarRepository.save(guitar);

        Assertions.assertThat(savedGuitar).isNotNull();
        Assertions.assertThat(savedGuitar.getId()).isGreaterThan(0);
    }

    @Test
    public void testReadAll() {
        List<Guitar> guitars = guitarRepository.findAll();

        Assertions.assertThat(guitars).hasSizeGreaterThan(0);
        for (Guitar guitar : guitars) {
            System.out.println(guitar);
        }
    }

    @Test
    public void testUpdate() {
        Long userId = 1L;
        Optional<Guitar> optionalGuitar = guitarRepository.findById(userId);
        Guitar guitar = optionalGuitar.get();
        guitar.setColour("Sherwood Green");
        guitarRepository.save(guitar);
        Guitar updatedGuitar = guitarRepository.findById(userId).get();

        Assertions.assertThat(updatedGuitar.getColour()).isEqualTo("Sherwood Green");
    }

    @Test
    public void testRead() {
        Long userId = 2L;
        Optional<Guitar> optionalGuitar = guitarRepository.findById(userId);

        Assertions.assertThat(optionalGuitar).isPresent();
        System.out.println(optionalGuitar.get());
    }

    @Test
    public void testDelete() {
        Long userId = 2L;
        guitarRepository.deleteById(userId);
        Optional<Guitar> optionalGuitar = guitarRepository.findById(userId);

        Assertions.assertThat(optionalGuitar).isNotPresent();
    }
}
