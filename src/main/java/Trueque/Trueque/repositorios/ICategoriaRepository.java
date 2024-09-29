package Trueque.Trueque.repositorios;

import Trueque.Trueque.modelos.Categoria;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

}
