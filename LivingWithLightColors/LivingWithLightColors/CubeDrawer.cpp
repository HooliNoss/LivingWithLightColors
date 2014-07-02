#include <sifteo.h>
#include "RYBtoRGBConverter.cpp"

using namespace Sifteo;

/**
* Class who draws all the cubes
*/
class CubeDrawer
{
	private:
		RYBtoRGBConverter rybToRGBConverter;

		float rgbRed;
		float rgbGreen;
		float rgbBlue;

	public:
		CubeDrawer();
		
		float GetRGBRed();
		float GetRGBGreen();
		float GetRGBBlue();

		void ResetContainer();

		void DrawRedCube(float redValue);
		void DrawYellowCube(float yellowValue);
		void DrawBlueCube(float blueValue);
		void DrawLightBlub(int brightnessValue, LightBulbState state);
		void DrawContainer(float redValue, float yellowValue, float blueValue);

}tCubeDrawer;

CubeDrawer::CubeDrawer()
{

}

/**
* gets the red RGB Value of the container
* @return value between 0 and 1
*/
float CubeDrawer::GetRGBRed()
{
	return rgbRed;
}

/**
* gets the green RGB Value of the container
* @return value between 0 and 1
*/
float CubeDrawer::GetRGBGreen()
{
	return rgbGreen;
}

/**
* gets the red blue Value of the container
* @return value between 0 and 1
*/
float CubeDrawer::GetRGBBlue()
{
	return rgbBlue;
}

/**
* resets all RGB Values of the container
*/
void CubeDrawer::ResetContainer()
{
	rgbRed = 0;
	rgbGreen = 0;
	rgbBlue = 0;
}

/**
* draws the red cube
* @param redValue value between 0 and 1
*/
void CubeDrawer::DrawRedCube(float redValue)
{

	BackgroundImages bgImage = BgRed10;

	if (redValue >= 0)
		bgImage = BgRed10;

	if (redValue >= 0.1)
		bgImage = BgRed9;

	if (redValue >= 0.2)
		bgImage = BgRed8;

	if (redValue >= 0.3)
		bgImage = BgRed7;

	if (redValue >= 0.4)
		bgImage = BgRed6;

	if (redValue >= 0.5)
		bgImage = BgRed5;

	if (redValue >= 0.6)
		bgImage = BgRed4;

	if (redValue >= 0.7)
		bgImage = BgRed3;

	if (redValue >= 0.8)
		bgImage = BgRed2;

	if (redValue >= 1)
		bgImage = BgRed1;


	vbuf[redCube].bg0.image(vec(0,0), Backgrounds, bgImage);

}

/**
* draws the yellow cube
* @param yellowValue value between 0 and 1
*/
void CubeDrawer::DrawYellowCube(float yellowValue)
{

	BackgroundImages bgImage = BgYellow10;

	if (yellowValue >= 0)
		bgImage = BgYellow10;

	if (yellowValue >= 0.1)
		bgImage = BgYellow9;

	if (yellowValue >= 0.2)
		bgImage = BgYellow8;

	if (yellowValue >= 0.3)
		bgImage = BgYellow7;

	if (yellowValue >= 0.4)
		bgImage = BgYellow6;

	if (yellowValue >= 0.5)
		bgImage = BgYellow5;

	if (yellowValue >= 0.6)
		bgImage = BgYellow4;

	if (yellowValue >= 0.7)
		bgImage = BgYellow3;

	if (yellowValue >= 0.8)
		bgImage = BgYellow2;

	if (yellowValue >= 1)
		bgImage = BgYellow1;


	vbuf[yellowCube].bg0.image(vec(0,0), Backgrounds, bgImage);
}

/**
* draws the blue cube
* @param blueValue value between 0 and 1
*/
void CubeDrawer::DrawBlueCube(float blueValue)
{
	BackgroundImages bgImage = BgBlue10;

	if (blueValue >= 0)
		bgImage = BgBlue10;

	if (blueValue >= 0.1)
		bgImage = BgBlue9;

	if (blueValue >= 0.2)
		bgImage = BgBlue8;

	if (blueValue >= 0.3)
		bgImage = BgBlue7;

	if (blueValue >= 0.4)
		bgImage = BgBlue6;

	if (blueValue >= 0.5)
		bgImage = BgBlue5;

	if (blueValue >= 0.6)
		bgImage = BgBlue4;

	if (blueValue >= 0.7)
		bgImage = BgBlue3;

	if (blueValue >= 0.8)
		bgImage = BgBlue2;

	if (blueValue >= 1)
		bgImage = BgBlue1;


	vbuf[blueCube].bg0.image(vec(0,0), Backgrounds, bgImage);
}

/**
* draws the lightBulb cube
* @param brightnessValue value between 0 and 255
* @param state stete of the lightBulb 
*/
void CubeDrawer::DrawLightBlub(int brightnessValue, LightBulbState state)
{
	BackgroundImages bgImage = BgLightBulb0;

	if (state == LightBulbStateOn)
	{
		if (brightnessValue >= 0)
			bgImage = BgLightBulb0;

		if (brightnessValue >= 40)
			bgImage = BgLightBulb1;

		if (brightnessValue >= 80)
			bgImage = BgLightBulb2;

		if (brightnessValue >= 120)
			bgImage = BgLightBulb3;

		if (brightnessValue >= 160)
			bgImage = BgLightBulb4;

		if (brightnessValue >= 200)
			bgImage = BgLightBulb5;

		if (brightnessValue >= 254)
			bgImage = BgLightBulb6;

	}
	else
	{
		bgImage = BgLightBulbOff;
	}


	vbuf[lightBulbCube].bg0.image(vec(0,0), Backgrounds, bgImage);
}

/**
* draws the container cube
* @param redValue value between 0 and 1
* @param yellowValue value between 0 and 1
* @param blueValue value between 0 and 1
*/
void CubeDrawer::DrawContainer(float redValue, float yellowValue, float blueValue)
{
	if (redValue + yellowValue + blueValue == 0)
	{
		vbuf[containerCube].initMode(BG0_SPR_BG1);
		vbuf[containerCube].attach(containerCube);
		vbuf[containerCube].bg0.image(vec(0,0), Backgrounds, BgEmptyContainer);
	}
	else
	{
		rybToRGBConverter.rybToRgb(redValue, yellowValue, blueValue);
		rgbRed = rybToRGBConverter.getRgbRed() / 255;
		rgbGreen = rybToRGBConverter.getRgbGreen() / 255;
		rgbBlue = rybToRGBConverter.getRgbBlue() / 255;

		RGB565 ColorContainer = RGB565::fromRGB(rgbRed, rgbGreen, rgbBlue);

		vbuf[containerCube].initMode(FB32);
		//vbuf[containerCube].initMode(STAMP);
		for (int i=0; i<13; i++)
		{
			vbuf[containerCube].colormap[i] = ColorContainer;
		}
	}
}