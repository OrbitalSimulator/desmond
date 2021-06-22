# Desmond

To build the project and conduct tests, clone the repository and run:
	
	gradle build	
	
To run the project as an application, clone the repository and run:

	gradle run
	

The project is divided into 10 packages:

	conf: Default configuration settings and file management
	data: Stores data from saved universes
	land: Lander implementations
	misc: Images, skins and resources
	peng: All elements of the physics engine (rates, states, interfaces, functions)
	prob: Probe with fuel implementation
	solv: ODE solver interface and solvers
	traj: Route planning, orbit planning, and Newton-Raphson implementation
	univ: Classes holding CelestialBody and Universe model
	visu: Graphical User Interface
	
Additionally the test package holds all JUnit testing and Logging classes in three packages

	expData: NASA Horizons data used for testing
	log: Data logging and saved test outputs
	test: JUnit 5 tests
	
      

    
    
