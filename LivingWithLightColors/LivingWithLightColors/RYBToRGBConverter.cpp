//#include "stdafx.h"
//#include <stdio.h>
//#include <math.h>
#include <sifteo.h>

using namespace Sifteo;

/**
* Class who converts the RYB value to an RGB value
*/
class RYBtoRGBConverter
{
	float rgbRed;
	float rgbGreen;
	float rgbBlue;

	public:
		void rybToRgb(float r, float y, float b);
		float getRgbRed();
		float getRgbGreen();
		float getRgbBlue();

	private:
		float cubicInt(float t, float A, float B);
		float getR(float iR, float iY, float iB);
		float getG(float iR, float iY, float iB);
		float getB(float iR,float iY,float iB);

}tRYBtoRGBConverter;

 float RYBtoRGBConverter::cubicInt(float t, float A, float B)
 {
    float weight = t*t*(3-2*t);
    return A + weight*(B-A);
 }


float RYBtoRGBConverter::getR(float iR, float iY, float iB) 
 {
    // red
    float x0 = cubicInt(iB, 1.0, 0.163);
    float x1 = cubicInt(iB, 1.0, 0.0);
    float x2 = cubicInt(iB, 1.0, 0.5);
    float x3 = cubicInt(iB, 1.0, 0.2);
    float y0 = cubicInt(iY, x0, x1);
    float y1 = cubicInt(iY, x2, x3);


    return ceil(255 * cubicInt(iR, y0, y1));
  }

float RYBtoRGBConverter::getG(float iR, float iY, float iB) 
 {
    // green
    float x0 = cubicInt(iB, 1.0, 0.373);
    float x1 = cubicInt(iB, 1.0, 0.66);
    float x2 = cubicInt(iB, 0.0, 0.0);
    float x3 = cubicInt(iB, 0.5, 0.094);
    float y0 = cubicInt(iY, x0, x1);
    float y1 = cubicInt(iY, x2, x3);

    return ceil(255 * cubicInt(iR, y0, y1));
  }

float RYBtoRGBConverter::getB(float iR,float iY,float iB) 
 {
    // blue
    float x0 = cubicInt(iB, 1.0, 0.6);
    float x1 = cubicInt(iB, 0.0, 0.2);
    float x2 = cubicInt(iB, 0.0, 0.5);
    float x3 = cubicInt(iB, 0.0, 0.0);
    float y0 = cubicInt(iY, x0, x1);
    float y1 = cubicInt(iY, x2, x3);

    return ceil(255 * cubicInt(iR, y0, y1));
  }

/**
* Converts the RYB Value to an RGB value
* @param r red value between 0 and 1
* @param y yellow value between 0 and 1
* @param b blue value between 0 and 1
*/
void RYBtoRGBConverter::rybToRgb(float r, float y, float b)
{

	float red = r;
	float yellow = y;
	float blue = b;

	rgbRed = getR(red, yellow, blue);
	rgbGreen = getG(red, yellow, blue);
	rgbBlue = getB(red, yellow, blue);
}

/**
* Gets the converted red RGB value
* @return red value between 0 and 1
*/
float RYBtoRGBConverter::getRgbRed()
{
	return rgbRed;
}

/**
* Gets the converted green RGB value
* @return green value between 0 and 1
*/
float RYBtoRGBConverter::getRgbGreen()
{
	return rgbGreen;
}

/**
* Gets the converted blue RGB value
* @return blue value between 0 and 1
*/
float RYBtoRGBConverter::getRgbBlue()
{
	return rgbBlue;
}


