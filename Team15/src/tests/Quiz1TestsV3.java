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
@SuppressWarnings({ "rawtypes", "unchecked","unused" })

public class Quiz1TestsV3 {


	String buildingPath = "buildings.Building";
	String economicBuildingPath = "buildings.EconomicBuilding";
	String farmPath = "buildings.Farm";
	String marketPath = "buildings.Market";
	String militaryBuildingPath = "buildings.MilitaryBuilding";
	String archeryRangePath = "buildings.ArcheryRange";
	String barracksPath = "buildings.Barracks";
	String stablePath = "buildings.Stable";
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
	String passiveUnitPath = "units.PassiveUnit";
	String farmerPath = "units.Farmer";
	
	
	
	@Test(timeout = 1000)
	public void testClassIsAbstractPassiveUnit() throws Exception {
		testClassIsAbstract(Class.forName(passiveUnitPath));
	}
	

	@Test(timeout = 1000)
	public void testConstructorPassiveUnit() throws Exception {
		Class[] inputs = { int.class,double.class };
		testConstructorExists(Class.forName(passiveUnitPath), inputs);
		
	}

	@Test(timeout = 1000)
	public void testPassiveUnitInstanceVariableLevel() throws Exception {
		testInstanceVariableIsPresent(Class.forName(passiveUnitPath), "level", true);

		testInstanceVariableIsPresent(Class.forName(farmerPath), "level", false);
		

		testInstanceVariableIsPrivate(Class.forName(buildingPath), "level");
		testInstanceVariableOfType(Class.forName(buildingPath), "level", int.class);
	}

	@Test(timeout = 1000)
	public void testPassiveUnitLevelSetter() throws Exception {
		testSetterMethodExistsInClass(Class.forName(passiveUnitPath), "setLevel", int.class, true);
		String[] subClasses = { farmerPath};
		testSetterAbsentInSubclasses("level", subClasses);

		Constructor<?> constructor = Class.forName(farmerPath).getConstructor(int.class,double.class,double.class,int.class);
		Object b = constructor.newInstance(1,2.8,3.0,2);
		int randomLevel = (int) (Math.random() * 2) + 1;
		testSetterLogic(b, "level", randomLevel, randomLevel, int.class);

	}

	@Test(timeout = 1000)
	public void testPassiveUnitLevelGetter() throws Exception {

		testGetterMethodExistsInClass(Class.forName(passiveUnitPath), "getLevel", int.class, true);
		String[] subClasses = { farmerPath};
		testGetterAbsentInSubclasses("level", subClasses, int.class);
		Constructor<?> constructor = Class.forName(farmerPath).getConstructor(int.class,double.class,double.class,int.class);
		Object b = constructor.newInstance(1,2.8,3.0,2);
		int randomLevel = (int) (Math.random() * 2) + 1;
		testGetterLogic(b, "level", randomLevel);
	}
	
	@Test(timeout = 1000)
	public void testPassiveUnitInstanceVariableFoodConsumption() throws Exception {
		testInstanceVariableIsPresent(Class.forName(passiveUnitPath), "foodConsumption", true);

		testInstanceVariableIsPresent(Class.forName(farmerPath), "foodConsumption", false);
		

		testInstanceVariableIsPrivate(Class.forName(passiveUnitPath), "foodConsumption");
		testInstanceVariableOfType(Class.forName(passiveUnitPath), "foodConsumption", double.class);
	}

	@Test(timeout = 1000)
	public void testPassiveUnitFoodConsumptionSetter() throws Exception {
		testSetterMethodExistsInClass(Class.forName(passiveUnitPath), "setFoodConsumption", double.class, true);
		String[] subClasses = { farmerPath};
		testSetterAbsentInSubclasses("foodConsumption", subClasses);

		Constructor<?> constructor = Class.forName(farmerPath).getConstructor(int.class,double.class,double.class,int.class);
		Object b = constructor.newInstance(1,2.8,3.0,2);
		double randomFoodConsumption =  (Math.random() * 2) + 1;
		testSetterLogic(b, "foodConsumption", randomFoodConsumption, randomFoodConsumption, double.class);

	}

	@Test(timeout = 1000)
	public void testPassiveUnitFoodConsumptionGetter() throws Exception {

		testGetterMethodExistsInClass(Class.forName(passiveUnitPath), "getFoodConsumption", double.class, true);
		String[] subClasses = { farmerPath};
		testGetterAbsentInSubclasses("foodConsumption", subClasses, double.class);
		Constructor<?> constructor = Class.forName(farmerPath).getConstructor(int.class,double.class,double.class,int.class);
		Object b = constructor.newInstance(1,2.8,3.0,2);
		double randomFoodConsumption = (Math.random() * 2) + 1;
		testGetterLogic(b, "foodConsumption", randomFoodConsumption);
	}
	
	
	@Test(timeout = 1000)
	public void testClassIsSubclassFarmer() throws Exception {
		testClassIsSubclass(Class.forName(farmerPath), Class.forName(passiveUnitPath));
	}
	
	
	
	@Test(timeout = 1000)
	public void testConstructorFarmer() throws Exception {
		Class[] inputs = {int.class,double.class,double.class,int.class};
		testConstructorExists(Class.forName(farmerPath), inputs);
		Constructor<?> constructor = Class.forName(farmerPath).getConstructor(int.class,double.class,double.class,int.class);
		Object b = constructor.newInstance(1,2.8,4.5,2);
		String[] varNames = { "level","foodConsumption", "foodProduction","cooldown"};
		Object[] varValues = {1, 2.8,4.5, 2};
		testConstructorInitialization(b, varNames, varValues);
	}
	
	@Test(timeout = 1000)
	public void testFarmerInstanceVariableCooldown() throws Exception {
		testInstanceVariableIsPresent(Class.forName(farmerPath), "cooldown", true);

		testInstanceVariableIsPresent(Class.forName(passiveUnitPath), "coolDown", false);
	
		testInstanceVariableIsPrivate(Class.forName(farmerPath), "cooldown");
		testInstanceVariableOfType(Class.forName(farmerPath), "cooldown", int.class);
	}
	
	@Test(timeout = 1000)
	public void testFarmerCooldownSetter() throws Exception {
		testSetterMethodExistsInClass(Class.forName(farmerPath), "setCooldown", int.class, true);
	

		Constructor<?> constructor = Class.forName(farmerPath).getConstructor(int.class,double.class,double.class,int.class);
		Object b = constructor.newInstance(1,1.5,2.5,3);
		int randomCooldown =(int)(Math.random() * 3) + 1;
		testSetterLogic(b, "cooldown", randomCooldown, randomCooldown, int.class);
	}

	@Test(timeout = 1000)
	public void testFarmerCooldownGetter() throws Exception {

		testGetterMethodExistsInClass(Class.forName(farmerPath), "getCooldown", int.class, true);

		Constructor<?> constructor = Class.forName(farmerPath).getConstructor(int.class,double.class,double.class,int.class);
		Object b = constructor.newInstance(1,1.5,2.5,3);
		int randomCooldown = (int) (Math.random() * 1000) + 500;
		testGetterLogic(b, "cooldown", randomCooldown);
	}
	
	
	@Test(timeout = 1000)
	public void testFarmerInstanceVariableFoodProduction() throws Exception {
		testInstanceVariableIsPresent(Class.forName(farmerPath), "foodProduction", true);

		testInstanceVariableIsPresent(Class.forName(passiveUnitPath), "foodProduction", false);
	
		testInstanceVariableIsPrivate(Class.forName(farmerPath), "foodProduction");
		testInstanceVariableOfType(Class.forName(farmerPath), "foodProduction", double.class);
	}
	
	
	@Test(timeout = 1000)
	public void testFarmerFoodProductionSetter() throws Exception {
		testSetterMethodExistsInClass(Class.forName(farmerPath), "setFoodProduction", double.class, true);
	

		Constructor<?> constructor = Class.forName(farmerPath).getConstructor(int.class,double.class,double.class,int.class);
		Object b = constructor.newInstance(1,2.8,2.5,3);
		double randomFoodProduction =(Math.random() * 1000) + 500;
		testSetterLogic(b, "foodProduction", randomFoodProduction, randomFoodProduction, double.class);
	}

	@Test(timeout = 1000)
	public void testFarmerFoodProductionGetter() throws Exception {

		testGetterMethodExistsInClass(Class.forName(farmerPath), "getFoodProduction", double.class, true);

		Constructor<?> constructor = Class.forName(farmerPath).getConstructor(int.class,double.class,double.class,int.class);
		Object b = constructor.newInstance(1,2.8,2.5,3);
		double randomFoodProduction =  (Math.random() * 1000) + 500;
		testGetterLogic(b, "foodProduction", randomFoodProduction);
	}
	
	
	//###########################################################################################
	
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
		assertEquals("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
				+ " should not be accessed outside that class.", false, f.isAccessible());
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
