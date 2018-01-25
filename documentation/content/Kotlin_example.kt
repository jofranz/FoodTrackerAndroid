mMapView.getMapAsync { mMap ->
	googleMap = mMap

	// For showing a move to my location button
	if ((activity as SecondMainActivity).checkLocationPermission()) {
		googleMap!!.isMyLocationEnabled = true
	}
	googleMap!!.setOnInfoWindowClickListener(this)
	var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
	val mealList = mdb.mealModel().getAllMeal()

	for (item in mealList) {
		var imageBitmap = MediaStore.Images.Media.getBitmap(view!!.context.contentResolver, Uri.parse(item.imagePath))
		val time: GregorianCalendar = GregorianCalendar()
		time.timeInMillis = item.time
		val markerRepresentation = MarkerRepresentation(imageBitmap, time, item.foodname)

		if (googleMap != null) {
			var markerOpt = MarkerOptions().position(LatLng(item.lat, item.lng)).title(item.foodname)
					.snippet(item.shortDescription)
			var marker1 = googleMap!!.addMarker(markerOpt)
			marker1.tag = markerRepresentation
			markers.add(marker1)
			googleMap!!.addMarker(markerOpt)?.tag = markerRepresentation
		}
	}
}