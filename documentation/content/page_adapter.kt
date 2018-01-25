class PageAdapter(fragmentManager: FragmentManager,numberOfTabs: Int): FragmentStatePagerAdapter(fragmentManager){

    private var mNumOfTabs = 0
    
    init {
        mNumOfTabs = numberOfTabs
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                return RootFragment()
            }
            1 -> {
                return MapFragment()
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}