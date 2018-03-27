package kz.nis.geography.cover;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class CoverPageTransformer implements PageTransformer {
	
	 private static final float MIN_SCALE = 0.75f;

	    public void transformPage(View view, float position) {
	        int pageWidth = view.getWidth();

	        if (position < -1) { // [-Infinity,-1)
	            // This page is way off-screen to the left.
	            view.setAlpha(0);

	        } else if (position <= 0) { // [-1,0]
	            // Use the default slide transition when moving to the left page
	            view.setAlpha(1);
	            view.setTranslationX(0);
	            view.setScaleX(1);
	            view.setScaleY(1);

	        } else if (position <= 1) { // (0,1]
	            // Fade the page out.
	            view.setAlpha(1 - position);

	            // Counteract the default slide transition
	            view.setTranslationX(pageWidth * -position);

	            // Scale the page down (between MIN_SCALE and 1)
	            float scaleFactor = MIN_SCALE
	                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
	            view.setScaleX(scaleFactor);
	            view.setScaleY(scaleFactor);

	        } else { // (1,+Infinity]
	            // This page is way off-screen to the right.
	            view.setAlpha(0);
	        }
	    }
//
//		private Button btn;
//		private ImageView bkg;

//	    public CoverPageTransformer(Button btn) {
//			this.btn = btn;
//		}
//	    
//	    public CoverPageTransformer(Button btn,ImageView bkg) {
//			this.btn = btn;
//			this.bkg = bkg;
//		}
	    
//	    private final int viewToParallax;
//
//	    public CoverPageTransformer(final int viewToParallax) {
//	        this.viewToParallax = viewToParallax;
//
//	    }
//
//	    @Override
//	    public void transformPage(View view, float position) {
//	        int pageWidth = view.getWidth();
//
//
//	        if (position < -1) { // [-Infinity,-1)
//	            // This page is way off-screen to the left.
//	            view.setAlpha(1);
//
//	        } else if (position <= 1) { // [-1,1]
//
//	            view.findViewById(viewToParallax).setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
//
//	        } else { // (1,+Infinity]
//	            // This page is way off-screen to the right.
//	            view.setAlpha(1);
//	        }
//
//
//	    }
//	    
//	  
//	  private static final float MIN_SCALE = 0.85f;
//	    private static final float MIN_ALPHA = 0.5f;
//	    public void transformPage(View view, float position) {
//			
//			int pageWidth = view.getWidth();
//			
//
//			 if (position < -1) { // [-Infinity,-1)
//		            // This page is way off-screen to the left.
//		            view.setAlpha(1);
//		        } else if (position <= 1) { // [-1,1]
//		            
//		        	btn.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
//
//					 bkg.setTranslationX(position * (pageWidth / 10)); //Half the normal speed
//		            
//		        } else { // (1,+Infinity]
//		            // This page is way off-screen to the right.
//		            view.setAlpha(1);
//				}
//
//		}
//		public void transformPage(View view, float position) {
//	        int pageWidth = view.getWidth();
//	        int pageHeight = view.getHeight();
//
//	        if (position < -1) { // [-Infinity,-1)
//	            // This page is way off-screen to the left.
//	            view.setAlpha(0);
//
//	        } else if (position <= 1) { // [-1,1]
//	            // Modify the default slide transition to shrink the page as well
//	            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//	            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
//	            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
//	            if (position < 0) {
//	                view.setTranslationX(horzMargin - vertMargin / 2);
//	            } else {
//	                view.setTranslationX(-horzMargin + vertMargin / 2);
//	            }
//
//	            // Scale the page down (between MIN_SCALE and 1)
//	            view.setScaleX(scaleFactor);
//	            view.setScaleY(scaleFactor);
//
//	            // Fade the page relative to its size.
//	            view.setAlpha(MIN_ALPHA +
//	                    (scaleFactor - MIN_SCALE) /
//	                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));
//
//	        } else { // (1,+Infinity]
//	            // This page is way off-screen to the right.
//	            view.setAlpha(0);
//	        }
//	    }
//	 public void transformPage(View view, float position) {
//
//         if (position < -1 || position > 1) {
//             view.setAlpha(0);
//             image1.setAlpha(0);
//         }
//         else if (position <= 0 || position <= 1) {
//             // Calculate alpha. Position is decimal in [-1,0] or [0,1]
//             float alpha = (position <= 0) ? position + 1 : 1 - position;
//             view.setAlpha(alpha);
//             image1.setAlpha(alpha);
//         }
//         else if (position == 0) {
//             view.setAlpha(1);
//             image1.setAlpha(1);
//         }
//     }
//	public void transformPage(View view, float position) {
//	    int pageWidth = view.getWidth();
//	        
//	    if (position < -1) { // [-Infinity,-1)
//	        // This page is way off-screen to the left.
//	        view.setAlpha(0);
//
//	    } else if (position <= 1) { // [-1,1]
//	          
//			  
//	        mBlur.setTranslationX((float) (-(1 - position) * 0.5 * pageWidth));
//			mBlurLabel.setTranslationX((float) (-(1 - position) * 0.5 * pageWidth));
//
//			mDim.setTranslationX((float) (-(1 - position) * pageWidth));
//			mDimLabel.setTranslationX((float) (-(1 - position) * pageWidth));
//
//			mCheck.setTranslationX((float) (-(1 - position) * 1.5 * pageWidth));
//			mDoneButton.setTranslationX((float) (-(1 - position) * 1.7 * pageWidth)); 
//			// The 0.5, 1.5, 1.7 values you see here are what makes the view move in a different speed.
//			// The bigger the number, the faster the view will translate.
//			// The result float is preceded by a minus because the views travel in the opposite direction of the movement.
//
//			mFirstColor.setTranslationX((position) * (pageWidth / 4));
//
//			mSecondColor.setTranslationX((position) * (pageWidth / 1));
//
//			mTint.setTranslationX((position) * (pageWidth / 2));
//
//			mDesaturate.setTranslationX((position) * (pageWidth / 1));
//			// This is another way to do it
//			  
//			  
//	    } else { // (1,+Infinity]
//	        // This page is way off-screen to the right.
//	        view.setAlpha(0);
//	    }
//	}
//	
//	@Override
//	public void transformPage(View view, float position) {
//		
//		int pageWidth = view.getWidth();
//		
//
//		 if (position < -1) { // [-Infinity,-1)
//	            // This page is way off-screen to the left.
//	            view.setAlpha(1);	          
//	        } else if (position <= 1) { // [-1,1]	            
//	        	image1.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
//	        } else { // (1,+Infinity]
//	            // This page is way off-screen to the right.
//	            view.setAlpha(1);
//			}
//	}
    }