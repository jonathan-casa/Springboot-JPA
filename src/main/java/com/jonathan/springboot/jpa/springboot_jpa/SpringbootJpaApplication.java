package com.jonathan.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jonathan.springboot.jpa.springboot_jpa.dto.PersonDTO;
import com.jonathan.springboot.jpa.springboot_jpa.entities.Person;
import com.jonathan.springboot.jpa.springboot_jpa.repositories.PersonRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{


	// usar este constructor en lugar de @Autowired.
	private final PersonRepository repository;
	public SpringbootJpaApplication(PersonRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	//LLAMAR A LOS METODOS AQUI
	@Override
	public void run(String... args) throws Exception {

		//consulta1();
		//consulta2finOne();
		//create()
		//update();
		//delete2();
		//consultaPersonalizada();
		//fulldataPerson();
		//consultaPersonalizada2();
		//consultaPersonalizadaDistinct();
		//consultaPersonalizadaConcat();
		//consultaBetween();
		//consultaBetween2();
		consultaCountMaxMin();
	}


	public void consulta1(){
		//List<Person> persons = (List<Person>) repository.findAll();
		//List<Person> persons = (List<Person>) repository.findByProgrammingLanguage("Java");
		List<Person> persons = (List<Person>) repository.buscarPorLenguajeyNombre("Java", "Maria");

		persons.stream().forEach(person -> {
			System.out.println(person);
		});

		//Otra manera de ejecutar @Querys desde PersonRepository. Uso de Arreglos
		List<Object[]> personsValues = repository.obtenerPersonData();
		personsValues.stream().forEach(person -> {
			System.out.println(person[0] + "es experto en " + person[1]);
		});	

	}

	public void consulta2finOne(){
		Person person = null;
		Optional<Person> opPerson = repository.findById(1L);
		if (opPerson.isPresent()){
			person = opPerson.get();
		}
		System.out.println(person);

		//Simplificar codigo de lineas 52-58 en una sola linea
		repository.findById(1L).ifPresent(System.out::println);

		// EJEMPLO 2
		repository.finOne(2L).ifPresent(System.out::println);;
	}


	//ACTUALIZAR DATOS
	@Transactional
	public void update(){

		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona");;
		Long id = sc.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		
		//METODO 1	
		//optionalPerson.ifPresent(person ->{
		//	System.out.println(person);
		//	System.out.println("Ingrese el lenguaje de programacion:");
		//	String programmingLanguage = sc.next();
		//	person.setProgramingLanguage(programmingLanguage);
		//	Person personDb = repository.save(person);
		//	System.out.println(personDb);
		//});

		//METODO 2, mejor opcion
		if (optionalPerson.isPresent()){
			Person person = optionalPerson.orElseThrow();
			System.out.println(person);
			System.out.println("Ingrese el nuevo lenguaje de programacion:");
			String programmingLanguage = sc.next();

			person.setProgramingLanguage(programmingLanguage);
			Person personDb = repository.save(person);
			System.out.println(personDb);
		} else{
			System.out.println("El usuario no existe!");
		}
		sc.close();
	}

	@Transactional
	public void create(){
		//introducir los datos por teclado
		Scanner sc = new Scanner(System.in);
		System.out.println("Nombre:");
		String name = sc.next();
		System.out.println("Apellido:");
		String lastname = sc.next();
		System.out.println("Lenguaje:");
		String programmingLanguage = sc.next();
		sc.close();
		//Añadirlos a la base de datos
		Person person = new Person(null, name, lastname, programmingLanguage);
		Person personNew = repository.save(person);
		System.out.println(personNew);
		
		//Buscarlo e imprimirlo si se han creado correctamente
		repository.findById(personNew.getId()).ifPresent(System.out::println);

	}

	@Transactional
	public void delete1(){
		//ver lista actual
		repository.findAll().forEach(System.out::println);
		
		//eliminar fila segun id
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar: ");
		Long id = sc.nextLong();
		repository.deleteById(id);

		//imprimir datos despues de eliminar
		repository.findAll().forEach(System.out::println);
		sc.close();
	}

	@Transactional
	public void delete2(){
		//ver lista actual
		repository.findAll().forEach(System.out::println);
		
		//eliminar fila segun id
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar: ");
		Long id = sc.nextLong();

		Optional<Person> opPerson = repository.findById(id);
		opPerson.ifPresentOrElse(person -> repository.delete(person), () -> System.out.println("No exixte ese usuario"));
		repository.deleteById(id);

		//imprimir datos despues de eliminar
		repository.findAll().forEach(System.out::println);
		sc.close();
	}

	@Transactional
	public void consultaPersonalizada(){
		Scanner sc = new Scanner(System.in);
		System.err.println("Ingrese el id del usuario");
		Long id = sc.nextLong();
		sc.close();

		String name = repository.getNameById(id);
		System.out.println(name);

		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

	}

	@Transactional
	public void fulldataPerson(){
		Scanner sc = new Scanner(System.in);
		System.err.println("Ingrese el id del usuario");
		Long id = sc.nextLong();
		sc.close();

		String name = repository.getNameById(id);
		System.out.println(name);

		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

		System.out.println("Consulta por campos personalizados por el ID");
		Optional<Object> opRegion = repository.obtenerFullDataPersonById(id);
		if (opRegion.isPresent()){
			Object[] personReg = (Object[]) opRegion.get();
			System.out.println("id: " + personReg[0] + ", nombre: "+ personReg[1]+ ", apellido: "+ personReg[2]+ ", lenguaje: "+ personReg[3]);
		}
		
	}

	@Transactional
	public void consultaPersonalizada2(){
		System.out.println("================================= consulta de objeto persona y lenguaje de programacion========================");

		List<Object[]> personReg = repository.findAllMixDataPerson();
		personReg.forEach(reg -> {
			System.out.println("programmingLanguage= " + reg[1] + ", person=" + reg[0]);
		});


		System.out.println("================================ consulta que puebla y devuelve objeto entity de una instancia personalizada================");
		List<Person> persons = repository.findAllObjectPersonPersonalizada();
		persons.forEach(System.out::println);

		System.out.println("============ consulta que puebla y devuelve objeto dto de una clase personalizada ================");
		List<PersonDTO> personDto = repository.findAllPersonDTO();
		personDto.forEach(System.out::println);
	}

	@Transactional
	public void consultaPersonalizadaDistinct(){

		System.out.println("==================Consultas con nombre de personas UNICOS, sin repeticiones (DISTINCT)=================");
		List<String> names =repository.findAllNamesDistinct();
		names.forEach(System.out::println);

		System.out.println("==================Consultas con nombre de Lenguajes UNICOS, sin repeticiones (DISTINCT)=================");
		List<String> lenguages =repository.findAllLanguageDistinct();
		lenguages.forEach(System.out::println);


		System.out.println("==================Consultas total de lenguajes unicos, COUNT=================");
		Long totalLanguage = repository.findAllProgrammingLanguageDistinctCount();
		System.out.println("Total de lenguajes registrados: "+ totalLanguage);

	
	}
	@Transactional
	public void consultaPersonalizadaConcat(){

		System.out.println("===Lista de nombres y Apellidos ====================");
		List<String> names = repository.findAllFullNameConcat();
		names.forEach(System.out::println);
		
		System.out.println("==============Uso de UPPER=================");
		List<String> uppers = repository.findAllFullNameUpper();
		uppers.forEach(System.out::println);

		System.out.println("==============Uso de LOWER==================");
		List<String> lowers = repository.findAllNamesLower();
		lowers.forEach(System.out::println);
	}

	@Transactional
	public void consultaBetween(){

		System.out.println("=========================================uso de BETWEEN============================");
		List<Person> persons = repository.findAllBetweenId();
		persons.forEach(System.out::println);

		System.out.println("======================================Uso de BETWEEN CON CARACTERES=============");
		List<Person> names = repository.findAllBetweenName();
		names.forEach(System.out::println);
	}

	@Transactional
	public void consultaBetween2(){
		System.out.println("========================Uso de BETWEEN con PARAMETROS ID=============");
		List<Person> persons2 = repository.findAllBetweenId2(2L, 5L);
		persons2.forEach(System.out::println);

		System.out.println("=======================Uso de BETWEEN con PARAMETROS NAME============");
		List<Person> names2 = repository.findAllBetwwenName2("C", "R");
		names2.forEach(System.out::println);

		System.out.println("========================TODOS LOS NOMBRES ORDENADOS==================");
		List<Person> todos = repository.findAllByOrderByNameAscLastnameDesc();
		todos.forEach(System.out::println);

	}

	@Transactional
	public void consultaCountMaxMin(){
		System.out.println("===========================USO DE COUNT, MIN Y MAX==================");
		Long totalPer = repository.totalPersons();
		Long min = repository.minId();
		Long max = repository.maxId();

		System.out.println("Total de personas: "+ totalPer);
		System.out.println("Minimo id: "+ min);
		System.out.println("Maximo id: "+ max);
		
	}
}
