GET
* android viewpager 滑动会先执行OnPageChangeListener后执行PagerAdapter
setCurrentItem会先执行PagerAdapter后执行OnPageChangeListener
* 预加载创建对象过慢，导致对象错乱
* SuperView高度固定
* setHorizontalSpacing解决GridView右边缝隙
* ViewPager每次滑动都会进行测量，在这里测量子View的高度，取最大高度最为固定高度
* 添加日期选择监听器，还需要优化，层级太深