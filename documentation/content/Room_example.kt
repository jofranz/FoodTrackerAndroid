@Entity(tableName = "meal")
data class Meal(@ColumnInfo(name = "foodname") var foodname: String,
	@ColumnInfo(name = "shortDescription") var shortDescription: String,
	@ColumnInfo(name= "longDescription") var longDescription: String,
	@ColumnInfo(name= "effectDescription") var effectDescription: String,
	@ColumnInfo(name = "effect")var effect: Int,
	@ColumnInfo(name = "time")var time: Long,
	@ColumnInfo(name = "lat") var lat: Double,
	@ColumnInfo(name = "lng") var lng: Double,
	@ColumnInfo(name = "addressline") var addressline: String,
	@ColumnInfo(name="imagePath") var imagePath: String)
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Int = 0