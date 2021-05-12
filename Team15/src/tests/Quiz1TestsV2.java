package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.junit.Test;

import units.Army;
@SuppressWarnings({ "rawtypes", "unchecked","unused" })
public class Quiz1TestsV2 {
	
	
	String buildingsPath = "buildingss.Building";
	String economicBuildingPath = "buildings.EconomicBuilding";
	String farmPath = "buildings.Farm";
	String marketPath = "buildings.Market";
	String militaryBuildingPath = "buildings.MilitaryBuilding";
	String archeryRangePath = "buildings.ArcheryRange";
	String barracksPath = "buildings.Barracks";
	String stablePath = "buildings.Stable";
	String agencyPath = "buildings.Agency";
	
	String distancePath = "engine.Distance";
	String gamePath = "engine.Game";
	String statusPath = "units.Status";
	String playerPath = "engine.Player";
	String cityPath = "engine.City";

	String mlePath = "exceptions.MaxLevelException";
	String bePath = "exceptions.BuildingException";
	String aePath = "exceptions.ArmyException";
	String ffePath = "exceptions.FriendlyFireException";
	String fcePath = "exceptions.FriendlyCityException";
	String tnrePath = "exceptions.TargetNotReachedException";
	String eePath = "exceptions.EmpireException";
	String bicdePath = "exceptions.BuildingInCoolDownException";
	String mrePath = "exceptions.MaxRecruitedException";
	String unitPath = "units.Unit";
	String armyPath = "units.Army";
	String archerPath = "units.Archer";
	String infantryPath = "units.Infantry";
	String cavalryPath = "units.Cavalry";
	String assassinPath = "units.Assassin";
	
	
	
	@Test(timeout = 1000)
	public void testConstructorAgency() throws Exception {
		Class[] inputs = {};
		testConstructorExists(Class.forName(agencyPath), inputs);
		Constructor<?> constructor = Class.forName(agencyPath).getConstructor();
		Object b = constructor.newInstance();
		String[] varNames = {"cost", "upgradeCost", "level", "coolDown", "recruitmentCost", "currentRecruit", "maxRecruit"};
		Object[] varValues = {2500, 1500, 1, true, 1200, 0, 3};
		testConstructorInitialization(b, varNames, varValues);
	}
	@Test(timeout = 10000)
	public void testClassIsSubclassAgency() throws Exception {
		testClassIsSubclass(Class.forName(agencyPath), Class.forName(militaryBuildingPath));
	}

	
	@Test(timeout = 1000)
	public void testInstanceVariableAssassinCities() throws Exception {
		testInstanceVariableIsPresent(Class.forName(assassinPath), "cities", true);
		testInstanceVariableIsPrivate(Class.forName(assassinPath), "cities");
		testInstanceVariableOfType(Class.forName(assassinPath), "cities", ArrayList.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAssassinCitiesGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(assassinPath), "getCities", ArrayList.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAssassinCitiesGetterLogic() throws Exception {
		Constructor<?> constructor = Class.forName(assassinPath).getConstructor(int.class,int.class,double.class,double.class);
		int soldCount = (int) (Math.random() * 50);
		int level = (int) (Math.random() * 3);
		double upKeep = Math.random();
		double marchUpKeep = Math.random();
		Object myObj = constructor.newInstance(level,soldCount,upKeep,marchUpKeep);
		ArrayList<?> value = new ArrayList<Object>();
		testGetterLogic(myObj, "cities", value);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAssassinCitiesSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(assassinPath), "setCities", ArrayList.class, true);
	}
	
	
    @Test(timeout = 1000)
	public void testInstanceVariableAssassinCitiesLogic() throws Exception {
		Constructor<?> constructor = Class.forName(assassinPath).getConstructor(int.class,int.class,double.class,double.class);
		ArrayList<String> x = new ArrayList<String>();
		x.add("Hamada");
		int soldCount = (int) (Math.random() * 50);
		int level = (int) (Math.random() * 3);
		double upKeep = Math.random();
		double marchUpKeep = Math.random();
		Object myObj = constructor.newInstance(level,soldCount,upKeep,marchUpKeep);
	
		testSetterLogic(myObj, "cities", x,x, ArrayList.class);
	}
    
    @Test(timeout = 1000)
	public void testInstanceVariableAssassinArmies() throws Exception {
		testInstanceVariableIsPresent(Class.forName(assassinPath), "armies", true);
		testInstanceVariableIsPrivate(Class.forName(assassinPath), "armies");
		testInstanceVariableOfType(Class.forName(assassinPath), "armies", ArrayList.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAssassinArmiesGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(assassinPath), "getArmies", ArrayList.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAssassinArmiesGetterLogic() throws Exception {
		Constructor<?> constructor = Class.forName(assassinPath).getConstructor(int.class,int.class,double.class,double.class);
		int soldCount = (int) (Math.random() * 50);
		int level = (int) (Math.random() * 3);
		double upKeep = Math.random();
		double marchUpKeep = Math.random();
		Object myObj = constructor.newInstance(level,soldCount,upKeep,marchUpKeep);
		ArrayList<?> value = new ArrayList<Object>();
		testGetterLogic(myObj, "armies", value);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableAssassinArmiesSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(assassinPath), "setArmies", ArrayList.class, true);
	}
	
	
    @Test(timeout = 1000)
	public void testInstanceVariableAssassinArmiesLogic() throws Exception {
		Constructor<?> constructor = Class.forName(assassinPath).getConstructor(int.class,int.class,double.class,double.class);
		ArrayList<Army> x = new ArrayList<Army>();
		x.add(new Army("Rome"));
		int soldCount = (int) (Math.random() * 50);
		int level = (int) (Math.random() * 3);
		double upKeep = Math.random();
		double marchUpKeep = Math.random();
		Object myObj = constructor.newInstance(level,soldCount,upKeep,marchUpKeep);
	
		testSetterLogic(myObj, "armies", x,x, ArrayList.class);
	}

    
	@Test(timeout = 1000)
	public void testClassIsSubclassAssassin() throws Exception {
		testClassIsSubclass(Class.forName(assassinPath), Class.forName(unitPath));
	}

	
	@Test(timeout = 1000)
	public void testConstructorAssassinConstructor()
			throws Exception {
		
		Class[] inputs = { int.class, int.class, double.class, double.class };
		testConstructorExists(Class.forName(assassinPath), inputs);
		
		Constructor<?> constructor = Class.forName(assassinPath).getConstructor(int.class,int.class,double.class,double.class);
		Object b = constructor.newInstance(1,3,0.2,0.6);
		String[] varNames = {"level","maxSoldierCount","idleUpkeep","marchingUpkeep","siegeUpkeep"};
		Object[] varValues = {1,3,0.2,0.6,-1.0};
		testConstructorInitialization(b, varNames, varValues);
	}
	
	//#####################################################################################
	
	private void testInstanceVariableIsPresent(Class aClass, String varName, boolean implementedVar)
			throws SecurityException {

		boolean thrown = false;
		try {
			aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		if (implementedVar) {
			assertFalse(
					"There should be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".",
					thrown);
		} else {
			assertTrue("The instance variable \"" + varName + "\" should not be declared in class "
					+ aClass.getSimpleName() + ".", thrown);
		}
	}

	private void testInstanceVariableOfType(Class aClass, String varName, Class expectedType) {
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			return;
		}
		Class varType = f.getType();
		assertEquals(
				"the attribute " + varType.getSimpleName() + " should be of the type " + expectedType.getSimpleName(),
				expectedType, varType);
	}

	private void testInstanceVariableIsPrivate(Class aClass, String varName)
			throws NoSuchFieldException, SecurityException {
		Field f = aClass.getDeclaredField(varName);
		assertEquals(
				varName + " instance variable in class " + aClass.getName()
						+ " should not be accessed outside that class", 2,
				f.getModifiers());
	}

	private void testInstanceVariableIsFinal(Class aClass, String varName)
			throws NoSuchFieldException, SecurityException {
		Field f = aClass.getDeclaredField(varName);
		assertEquals("The value of \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
				+ " should not be open for changes.", 18, f.getModifiers());

	}

	private void testGetterMethodExistsInClass(Class aClass, String methodName, Class returnedType,
			boolean readvariable) {
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			found = false;
		}

		String varName = "";
		if (returnedType == boolean.class)
			varName = methodName.substring(2).toLowerCase();
		else
			varName = methodName.substring(3).toLowerCase();
		if (readvariable) {
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is a READ variable.", found);
			assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + " class.",
					m.getReturnType().isAssignableFrom(returnedType));
		} else {
			assertFalse("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is not a READ variable.", found);
		}

	}

	private void testSetterMethodExistsInClass(Class aClass, String methodName, Class inputType,
			boolean writeVariable) {

		Method[] methods = aClass.getDeclaredMethods();
		String varName = methodName.substring(3).toLowerCase();
		if (writeVariable) {
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is a WRITE variable.", containsMethodName(methods, methodName));
		} else {
			assertFalse("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
					+ " is not a WRITE variable.", containsMethodName(methods, methodName));
			return;
		}
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName, inputType);
		} catch (NoSuchMethodException e) {
			found = false;
		}

		assertTrue(aClass.getSimpleName() + " class should have " + methodName + " method that takes one "
				+ inputType.getSimpleName() + " parameter.", found);

		assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + ".",
				m.getReturnType().equals(Void.TYPE));

	}

	private void testSetterAbsentInSubclasses(String varName, String[] subclasses)
			throws SecurityException, ClassNotFoundException {
		String methodName = "set" + varName.substring(0, 1).toUpperCase() + varName.substring(1);
		boolean methodIsInSubclasses = false;
		for (String subclass : subclasses) {
			Method[] methods = Class.forName(subclass).getDeclaredMethods();
			methodIsInSubclasses = methodIsInSubclasses || containsMethodName(methods, methodName);

		}
		assertFalse("The " + methodName + " method should not be implemented in a subclasses.", methodIsInSubclasses);
	}

	private void testGetterAbsentInSubclasses(String varName, String[] subclasses, Class type)
			throws SecurityException, ClassNotFoundException {
		String methodName = "get" + varName.substring(0, 1).toUpperCase() + varName.substring(1);
		if (type == boolean.class) {
			methodName = "is" + varName.substring(0, 1).toUpperCase() + varName.substring(1);
		}
		boolean methodIsInSubclasses = false;
		for (String subclass : subclasses) {
			Method[] methods = Class.forName(subclass).getDeclaredMethods();
			methodIsInSubclasses = methodIsInSubclasses || containsMethodName(methods, methodName);

		}
		assertFalse("The " + methodName + " method should not be implemented in subclasses.", methodIsInSubclasses);
	}

	private static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}

	private void testConstructorExists(Class aClass, Class[] inputs) {
		boolean thrown = false;
		try {
			aClass.getConstructor(inputs);
		} catch (NoSuchMethodException e) {
			thrown = true;
		}

		if (inputs.length > 0) {
			String msg = "";
			int i = 0;
			do {
				msg += inputs[i].getSimpleName() + " and ";
				i++;
			} while (i < inputs.length);

			msg = msg.substring(0, msg.length() - 4);

			assertFalse(
					"Missing constructor with " + msg + " parameter" + (inputs.length > 1 ? "s" : "") + " in "
							+ aClass.getSimpleName() + " class.",

					thrown);
		} else
			assertFalse("Missing constructor with zero parameters in " + aClass.getSimpleName() + " class.",

					thrown);

	}
	
	
	

	private void testClassIsAbstract(Class aClass) {
		assertTrue("You should not be able to create new instances from " + aClass.getSimpleName() + " class.",
				Modifier.isAbstract(aClass.getModifiers()));
	}

	private void testIsInterface(Class aClass) {
		assertEquals(aClass.getName() + " should be an Interface", true, aClass.isInterface());

	}

	private void testIsEnum(Class aClass) {

		assertEquals(aClass.getName() + " should be an Enum", true, aClass.isEnum());

	}

	private void testConstructorInitialization(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int i = 0; i < names.length; i++) {

			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];

			while (f == null) {

				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}

			}

			f.setAccessible(true);

			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
							+ " class should initialize the instance variable \"" + currName + "\" correctly.",
					currValue, f.get(createdObject));

		}

	}

	private void testGetterLogic(Object createdObject, String name, Object value) throws Exception {

		Field f = null;
		Class curr = createdObject.getClass();

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}

		}

		f.setAccessible(true);
		f.set(createdObject, value);

		Character c = name.charAt(0);

		String methodName = "get" + Character.toUpperCase(c) + name.substring(1, name.length());

		if (value.getClass().equals(Boolean.class))
			methodName = "is" + Character.toUpperCase(c) + name.substring(1, name.length());

		Method m = createdObject.getClass().getMethod(methodName);
		assertEquals(
				"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
						+ " should return the correct value of variable \"" + name + "\".",
				value, m.invoke(createdObject));

	}

	private void testSetterLogic(Object createdObject, String name, Object setValue, Object expectedValue, Class type)
			throws Exception {

		Field f = null;
		Class curr = createdObject.getClass();

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}

		}

		f.setAccessible(true);

		Character c = name.charAt(0);
		String methodName = "set" + Character.toUpperCase(c) + name.substring(1, name.length());
		Method m = createdObject.getClass().getMethod(methodName, type);
		m.invoke(createdObject, setValue);

		assertEquals(
				"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
						+ " should set the correct value of variable \"" + name + "\".",
				expectedValue, f.get(createdObject));

	}

	private void testClassIsSubclass(Class subClass, Class superClass) {
		assertEquals(subClass.getSimpleName() + " class should be a subclass from " + superClass.getSimpleName() + ".",
				superClass, subClass.getSuperclass());
	}



}
