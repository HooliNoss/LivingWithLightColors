#include <sifteo.h>
#include "assets.gen.h"

using namespace Sifteo;

/**
* Validats the incoming tilt values and returns the specific tilt direction
*/
class AccelerationAssessor
{

	const int cDeltaPositivLow = 20;
	const int cDeltaPositivModerate = 40;
	const int cDeltaPositivHigh = 50;
	const int cDeltaNegativLow = -20;
	const int cDeltaNegativModerate = -40;
	const int cDeltaNegativHigh = -50;
	const int cMaxPositiv = 230;
	const int cMaxNegativ = -230;

	public:
		AccelerationAssessor();
		TiltDirection CubeIsTilting(Byte3 myAccel);

	private:


}tAccelerationAssessor;

AccelerationAssessor::AccelerationAssessor()
{

}

/**
* Checks if the cube is tilting
* @return returns the tilt direction of the cube
*/
TiltDirection AccelerationAssessor::CubeIsTilting(Byte3 pAccel)
{
	TiltDirection direction = NoTilting;

	if (pAccel.x > cDeltaPositivLow)
	{
		direction = XPositivLow;
	}
	if (pAccel.x > cDeltaPositivModerate)
	{
		direction = XPositivModerate;
	}
	if (pAccel.x > cDeltaPositivHigh && pAccel.x < cMaxPositiv)
	{
		direction = XPositivHigh;
	}


	if (pAccel.x < cDeltaNegativLow)
	{
		direction = XNegativLow;
	}
	if (pAccel.x < cDeltaNegativModerate)
	{
		direction = XNegativModerate;
	}
	if (pAccel.x < cDeltaNegativHigh && pAccel.x > cMaxNegativ)
	{
		direction = XNegativHigh;
	}


	if (pAccel.y > cDeltaPositivLow)
	{
		direction = YPositivLow;
	}
	if (pAccel.y > cDeltaPositivModerate)
	{
		direction = YPositivModerate;
	}
	if (pAccel.y > cDeltaPositivHigh && pAccel.y < cMaxPositiv)
	{
		direction = YPositivHigh;
	}


	if (pAccel.y < cDeltaNegativLow)
	{
		direction = YNegativLow;
	}
	if (pAccel.y < cDeltaNegativModerate)
	{
		direction = YNegativModerate;
	}
	if (pAccel.y < cDeltaNegativHigh && pAccel.y > cMaxNegativ)
	{
		return YNegativModerate;
	}

	if (pAccel.z < cDeltaNegativLow )
	{
		direction = ZNegativLow;
	}
	if (pAccel.z < cDeltaNegativModerate )
	{
		direction = ZNegativModerate;
	}
	if (pAccel.z < cDeltaNegativHigh && pAccel.z > cMaxNegativ)
	{
		direction = ZNegativHigh;
	}

	//LOG("direction: %i  \n", direction);
	return direction;
}