@Dao interface MealDao{
	@Query("select * from meal")
	fun getAllMeal(): List<Meal>

	@Query("select * from meal where foodname = :foodname")
	fun findMealByName(foodname: String): Meal

	@Query("select * from meal where id = :id")
	fun findMealById(id: Int) : Meal

	@Query("select * from meal where time >= :time")
	fun findAllMealsAfter(time: Long) : List<Meal>

	@Query("select * from meal where :start <= time AND :end >= time")
	fun findAllMealsAfter(start: Long, end: Long) : List<Meal>

	@Query("select * from meal where addressLine == :addressline")
	fun findAllMealsWithAddress(addressline: String) : List<Meal>

	@Query("select * from meal where foodName = :foodName AND time = :time")
	fun findAllMealsByNameAndTime(foodName: String, time: Long) : Meal

	@Query("select * from meal where lat = :lat AND lng = :lng")
	fun findMealByLatLng(lat: Double, lng: Double): Meal

	@Query("select * from meal where effect <= 1")
	fun findMealyByEffect(): List<Meal>

	@Insert(onConflict = 1)
	fun insetMeal(meal: Meal)

	@Update
	fun updateMeal(meal: Meal)

	@Delete
	fun deleteMeal(meal: Meal)

}