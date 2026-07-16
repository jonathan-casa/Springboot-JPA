package com.jonathan.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jonathan.springboot.jpa.springboot_jpa.dto.PersonDTO;
import com.jonathan.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {


    //Usar consulta ya existente (fingByProgrammingLanguage) para usarlo en Aplicacion principal.
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    //Personalizar un metodo para ejecutar una consulta JPA
    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2") 
    List<Person> buscarPorLenguajeyNombre(String programmingLanguage, String name);

    //Otra manera de realizar @Querys
    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Object[]> obtenerPersonData2(String programmingLanguage, String name);


    //Cosnultas personalizadas con @Query y Query Methods EJEMPLO2
    @Query("select p from Person p where p.id=?1")
    Optional<Person> finOne(Long id);

    
    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);

    //Como concatenas valores: ejemplo nombre y apellido:
    @Query("select concat(p.name, ' ', p.lastname) as fullname from Person p where p.id=?1")
    String getFullNameById(Long id);


    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id=?1")
    Optional<Object> obtenerFullDataPersonById(Long id);

    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixDataPerson();

    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllObjectPersonPersonalizada();
    
    // ejemplo para la clase PersonDTO;
    @Query("select new com.jonathan.springboot.jpa.springboot_jpa.dto.PersonDTO(p.name, p.lastname) from Person p")
    List<PersonDTO> findAllPersonDTO();

    //ejemplo uso de DISTINCT para valores unicos, sin repeticiones
    @Query("select DISTINCT p.name from Person p")
    List<String> findAllNamesDistinct();

    @Query("select DISTINCT p.programmingLanguage from Person p")
    List<String> findAllLanguageDistinct();

    //Ejemplo de uso de COUNT (conteo)
    @Query("select COUNT(distinct(p.programmingLanguage)) from Person p")
		Long findAllProgrammingLanguageDistinctCount();

    @Query("select CONCAT(p.name, ' ', p.lastname) from Person p")
    List<String> findAllFullNameConcat();
  //ejemplos de LOWER Y UPPER

  @Query("select UPPER(CONCAT(p.name, ' ', p.lastname)) from Person p")
  List<String> findAllFullNameUpper();

  @Query("select LOWER(CONCAT(p.name, ' ', p.lastname)) from Person p")
  List<String> findAllNamesLower();

  @Query("select p from Person p where p.id between 2 and 5")
  List<Person> findAllBetweenId();

  @Query("select p from Person p where p.name between 'J'and 'P'")
  List<Person> findAllBetweenName();


//otro ejemplo de BETWEEN con PARAMETROS
  @Query("select p from Person p where p.id between ?1 and ?2 order by p.id desc")
  List<Person> findAllBetweenId2(Long id1, Long id2);

  @Query("select p from Person p where p.name between ?1 and ?2 order by p.name, p.lastname asc")
  List<Person> findAllBetwwenName2(String name1, String name2);

  //==========EXIXTEN METODOS PREDETERMINADOS para NO USAR @QUERY======================
  List<Person> findByIdBetweenOrderByIdDesc(Long id1, Long id2);
  List<Person> findByNameBetweenOrderByNameAscLastnameDesc(String name1, String name2);
  List<Person> findAllByOrderByNameAscLastnameDesc();

  //===================================================================================
 
  //          USO DE COUNT, MIN Y MAX
  @Query("select count(p) from Person p")
  Long totalPersons();
  @Query("select min(p.id) from Person p")
  Long minId();
  @Query("select max(p.id) from Person p")
  Long maxId();



}
