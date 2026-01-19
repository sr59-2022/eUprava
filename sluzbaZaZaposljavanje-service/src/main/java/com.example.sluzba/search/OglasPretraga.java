package com.example.sluzba.search;

import com.example.sluzba.model.Oglas;
import com.example.sluzba.model.TipOglasa;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OglasPretraga {

    private OglasPretraga() {
    }

    public static Specification<Oglas> aktivniOglasi(
            String nazivPozicije,
            TipOglasa tipOglasa) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(
                    cb.greaterThanOrEqualTo(
                            root.get("rokPrijave"),
                            LocalDate.now()
                    )
            );

            if (nazivPozicije != null && !nazivPozicije.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("nazivPozicije")),
                                "%" + nazivPozicije.toLowerCase() + "%"
                        )
                );
            }

            if (tipOglasa != null) {
                predicates.add(
                        cb.equal(
                                root.get("tipOglasa"),
                                tipOglasa
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
