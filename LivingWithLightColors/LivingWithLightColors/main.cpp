/**
 * BDA Project Living With Light Colors
 */

#include <sifteo.h>
#include <sifteo/video/color.h>
#include "assets.gen.h"

#include "Configuration.cpp"
#include "AccelerationAssessor.cpp"
#include "Communication.cpp"
#include "CubeDrawer.cpp"


using namespace Sifteo;

static AccelerationAssessor accelerationAssessor;
static Communication communication;
static CubeDrawer cubeDrawer;


float ContainerRed = 0;
float ContainerYellow = 0;
float ContainerBlue = 0;

int brightness = 254;

bool autoCommunication = false;

FillDirection fillDirectionRed = FillDirectionAdd;
FillDirection fillDirectionYellow = FillDirectionAdd;
FillDirection fillDirectionBlue = FillDirectionAdd;

SystemTime lastClick = SystemTime::now();
SystemTime lastAccelChange = SystemTime::now();
SystemTime lastAutoCommunicationSent = SystemTime::now();

LightBulbState lightBulbState = LightBulbStateOn;


/**
* draws the gui for the cube
* @param cid cube id for the cube to draw
*/
static void drawCube(CubeID cid)
{
	switch (cid)
	{
		case containerCube:
			cubeDrawer.DrawContainer(ContainerRed, ContainerYellow, ContainerBlue);
		break;
		case redCube:
			cubeDrawer.DrawRedCube(ContainerRed);
		break;
		case yellowCube:
			cubeDrawer.DrawYellowCube(ContainerYellow);
		break;
		case blueCube:
			cubeDrawer.DrawBlueCube(ContainerBlue);
		break;
		case lightBulbCube:
			cubeDrawer.DrawLightBlub(brightness, lightBulbState);
		break;

	}

}

/** activats the cube and makes it ready to use
* @param cid cube id for the cube to activate
*/
static void activateCube(CubeID cid) {
	
    // mark cube as active and render its canvas
    activeCubes.mark(cid);
	motion[cid].attach(cid);

	drawCube(cid);
}

/** used infinite loop to activate the cubes and get events
*/
static void paintWrapper() {
    // clear the palette
    newCubes.clear();
    lostCubes.clear();
    reconnectedCubes.clear();
    dirtyCubes.clear();

    // fire events
    System::paint();

    // dynamically load assets just-in-time
    if (!(newCubes | reconnectedCubes).empty()) {
        AudioTracker::pause();
        loader.start(config);
        while(!loader.isComplete()) {
            for(CubeID cid : (newCubes | reconnectedCubes)) {
                vbuf[cid].bg0rom.hBargraph(
                    vec(0, 4), loader.cubeProgress(cid, 128), BG0ROMDrawable::ORANGE, 8
                );
            }
            // fire events while we wait
            System::paint();
        }
        loader.finish();
        AudioTracker::resume();
    }

    // repaint cubes
    for(CubeID cid : dirtyCubes) {
        activateCube(cid);
    }
    
    // also, handle lost cubes, if you so desire :)
}

/** implements the behavior of the paintpot cube when accel is changed
* @param cid cube id.
* @param ctxt sifteo framework param. not used.
* @param tiltDirection the direction in which the cube is tilt
*/
static void startPaintPotAccelBehavior(void* ctxt, unsigned cid, TiltDirection tiltDirection)
{

	TimeDelta accelDelta = SystemTime::now().operator-(lastAccelChange);
	if (accelDelta.milliseconds() > cAccelDeltaPaintPotMin) 
	{

		float addValue = 0;

		if (tiltDirection == XPositivLow || tiltDirection == YPositivLow)
		{
			addValue = 0.01;
		}
		if (tiltDirection == XNegativLow || tiltDirection == YNegativLow)
		{
			addValue = -0.01;
		}
		if (tiltDirection == XPositivModerate || tiltDirection == YPositivModerate)
		{
			addValue = 0.02;
		}
		if (tiltDirection == XNegativModerate || tiltDirection == YNegativModerate)
		{
			addValue = -0.02;
		}
		if (tiltDirection == XPositivHigh || tiltDirection == YPositivHigh)
		{
			addValue = 0.03;
		}
		if (tiltDirection == XNegativHigh || tiltDirection == YNegativHigh)
		{
			addValue = -0.03;
		}

		switch (cid)
		{
			case redCube:
			
				ContainerRed +=addValue;

				if (ContainerRed > 1)
					ContainerRed = 1;
				if (ContainerRed < 0)
					ContainerRed = 0;

				drawCube(cid);
			break;

			case yellowCube:

				ContainerYellow +=addValue;

				if (ContainerYellow > 1)
					ContainerYellow = 1;
				if (ContainerYellow < 0)
					ContainerYellow = 0;

				drawCube(cid);
			break;

			case blueCube:

				ContainerBlue +=addValue;

				if (ContainerBlue > 1)
					ContainerBlue = 1;
				if (ContainerBlue < 0)
					ContainerBlue = 0;
				drawCube(cid);
			break;
		}

		if (autoCommunication)
		{
			TimeDelta autoAccelDelta = SystemTime::now().operator-(lastAutoCommunicationSent);

			if (autoAccelDelta.milliseconds() > cAccelDeltaLightBulbMin)
			{
				communication.LightBulbNewColor(cubeDrawer.GetRGBRed(), cubeDrawer.GetRGBGreen(), cubeDrawer.GetRGBBlue());

				lastAutoCommunicationSent = SystemTime::now();
			}
		}

		lastAccelChange = SystemTime::now();
	}
}

/** implements the behavior of the container cube when accel is changed
* @param cid cube id.
* @param ctxt sifteo framework param. not used.
* @param tiltDirection the direction in which the cube is tilt
*/
static void startContainerAccelBehavior(void* ctxt, unsigned cid, TiltDirection tiltDirection)
{
	TimeDelta accelDelta = SystemTime::now().operator-(lastAccelChange);
	if (accelDelta.milliseconds() > cAccelDeltaContainerMin) 
	{
		if (tiltDirection == ZNegativLow ||
			tiltDirection == ZNegativModerate ||
			tiltDirection == ZNegativHigh )
		{
			ContainerBlue = 0;
			ContainerYellow = 0;
			ContainerRed = 0;

			cubeDrawer.ResetContainer();

			drawCube(redCube);
			drawCube(yellowCube);
			drawCube(blueCube);
		}

		lastAccelChange = SystemTime::now();
	}
}

/** implements the behavior of the lightBulb cube when accel is changed
* @param cid cube id.
* @param ctxt sifteo framework param. not used.
* @param tiltDirection the direction in which the cube is tilt
*/
static void startLightBulbAccelBehavior(void* ctxt, unsigned cid, TiltDirection tiltDirection)
{
	TimeDelta accelDelta = SystemTime::now().operator-(lastAccelChange);
	if (accelDelta.milliseconds() > cAccelDeltaLightBulbMin) 
	{

		if (tiltDirection == XPositivLow || tiltDirection == YPositivLow ||
			tiltDirection == XPositivModerate || tiltDirection == YPositivModerate ||
			tiltDirection == XPositivHigh || tiltDirection == YPositivHigh)
		{
			if (brightness < 254)
				brightness += 30;

			if (brightness > 254)
				brightness = 254;
		}
		else if (tiltDirection == XNegativLow || tiltDirection == YNegativLow ||
				tiltDirection == XNegativModerate || tiltDirection == YNegativModerate ||
				tiltDirection == XNegativHigh || tiltDirection == YNegativHigh)
		{
			if (brightness >= 0)
				brightness -= 30;

			if (brightness < 0)
				brightness = 0;
		}

		communication.LightBulbNewBrightness(brightness);

		drawCube(lightBulbCube);

		lastAccelChange = SystemTime::now();
	}
}

/** function to catch the AccelChange event. starts the specific behavior on each cube
* @param cid cube id.
* @param ctxt sifteo framework param. not used
*/
static void onAccelChange(void* ctxt, unsigned cid)
{
	CubeID cube(cid);
	
	unsigned changeFlags = motion[cid].update();
	auto tilt = motion[cid].tilt;

	TiltDirection tiltdir = accelerationAssessor.CubeIsTilting(cube.accel());
	if (tiltdir != NoTilting)
	{
		switch (cid)
		{
			case containerCube:
				startContainerAccelBehavior(ctxt, cid, tiltdir);
			break;
			case redCube: 
				startPaintPotAccelBehavior(ctxt, cid, tiltdir);
			break;
			case yellowCube:
				startPaintPotAccelBehavior(ctxt, cid, tiltdir);
			break;
			case blueCube:
				startPaintPotAccelBehavior(ctxt, cid, tiltdir);
			break;
			case lightBulbCube:
				startLightBulbAccelBehavior(ctxt, cid, tiltdir);
			break;
		}

		drawCube(containerCube);
	}
}

/** implements the behavior of the lightBulb cube when the cube is touched
* @param cid cube id
*/
static void startLightBulbTouchBehavior(unsigned cid)
{
	if (lightBulbState == LightBulbStateOff)
	{
		communication.LightBulbOn();
		lightBulbState = LightBulbStateOn;
	}
	else if (lightBulbState == LightBulbStateOn)
	{
		communication.LightBulbOff();
		lightBulbState = LightBulbStateOff;
	}

	drawCube(lightBulbCube);
}

/** implements the behavior of the paintPot cube when the cube is touched
* @param cid cube id
*/
static void startPaintPotTouchBehavior(unsigned cid)
{
	switch (cid)
	{
		case redCube:

				if (fillDirectionRed == FillDirectionAdd)
					fillDirectionRed = FillDirectionRemove;

				else if (fillDirectionRed == FillDirectionRemove)
					fillDirectionRed = FillDirectionAdd;

				drawCube(cid);
		break;
		case yellowCube:

				if (fillDirectionYellow == FillDirectionAdd)
					fillDirectionYellow = FillDirectionRemove;

				else if (fillDirectionYellow == FillDirectionRemove)
					fillDirectionYellow = FillDirectionAdd;

				drawCube(cid);

		break;
		case blueCube:

				if (fillDirectionBlue == FillDirectionAdd)
					fillDirectionBlue = FillDirectionRemove;

				else if (fillDirectionBlue == FillDirectionRemove)
					fillDirectionBlue = FillDirectionAdd;

				drawCube(cid);
		break;
	}
}

/** implements the behavior of the conatiner cube when the cube is touched
* @param cid cube id
*/
static void startContainerTouchBehavior(unsigned cid)
{
	//Do Nothing
}

/** function to catch the touch event. starts the specific behavior on each cube
* @param cid cube id.
* @param ctxt sifteo framework param. not used
*/
static void onTouch(void* ctxt, unsigned cid)
{
	TimeDelta clickDelta = SystemTime::now().operator-(lastClick);


	if (clickDelta.milliseconds() > cClickDeltaMin)
	{
		switch (cid)
		{
			case containerCube: 
				startContainerTouchBehavior(cid);
			break;
			case redCube:
				startPaintPotTouchBehavior(cid);
			break;
			case yellowCube:
				startPaintPotTouchBehavior(cid);
			break;
			case blueCube:
				startPaintPotTouchBehavior(cid);
			break;
			case lightBulbCube:
				startLightBulbTouchBehavior(cid);
			break;
		}

		lastClick = SystemTime::now();
	}
}

/** checks if a cube is still active
* @param nid id of the neighbour
*/
static bool isActive(NeighborID nid) {
    // Does this nid indicate an active cube?
    return nid.isCube() && activeCubes.test(nid);
}

/** implements the behavior of the paintPot cube when an other cube is his neighbour
* @param cid cube id.
* @param side0 side where the cube is in touch with the other cube
*/
static void startPaintPotContainerNeighbourBehavior(unsigned cid, unsigned side0)
{
	//DoNothing
}

/** implements the behavior of the container cube when an other cube is his neighbour
* @param cid cube id.
* @param side0 side where the cube is in touch with the other cube
*/
static void startContainerLightBulbNeighbourBehavior(unsigned cid, unsigned side0)
{
	communication.LightBulbNewColor(cubeDrawer.GetRGBRed(), cubeDrawer.GetRGBGreen(), cubeDrawer.GetRGBBlue());
}

/** function to catch the neighbourAdd event. starts the specific behavior on each cube
* @param ctxt sifteo framework param. not used.
* @param cube0 cube id of the first cube.
* @param side0 side where the cube0 is in touch with the other cube1.
* @param cube1 cube id of the second cube.
* @param side1 side where the cube1 is in touch with the other cube0
*/
static void onNeighborAdd(void* ctxt, unsigned cube0, unsigned side0, unsigned cube1, unsigned side1) {

	if ((cube0 == containerCube || cube0 == lightBulbCube)
		&& (cube1 == containerCube || cube1 == lightBulbCube))
	{
		startContainerLightBulbNeighbourBehavior(cube0, side0);

		autoCommunication = true;
	}

}

/** function to catch the neighbourRemove event. starts the specific behavior on each cube
* @param ctxt sifteo framework param. not used.
* @param cube0 cube id of the first cube.
* @param side0 side where the cube0 is not anymore in touch with the other cube1.
* @param cube1 cube id of the second cube.
* @param side1 side where the cube1 is not anymore in touch with the other cube0
*/
static void onNeighborRemove(void* ctxt, unsigned cube0, unsigned side0, unsigned cube1, unsigned side1) {

	if ((cube0 == containerCube || cube0 == lightBulbCube)
		&& (cube1 == containerCube || cube1 == lightBulbCube))
	{
		autoCommunication = false;
	}
}

/** function to catch the cubeConnect event. Activates the cube and makes it available for using
* @param ctxt sifteo framework param. not used.
* @param cid cube id
*/
static void onCubeConnect(void* ctxt, unsigned cid) {
    // this cube is either new or reconnected
    if (lostCubes.test(cid)) {
        // this is a reconnected cube since it was already lost this paint()
        lostCubes.clear(cid);
        reconnectedCubes.mark(cid);
    } else {
        // this is a brand-spanking new cube
        newCubes.mark(cid);
    }
    // begin showing some loading art (have to use BG0ROM since we don't have assets)
    dirtyCubes.mark(cid);

	activateCube(cid);
}

/** function to catch the cubeDisconnect event. Disconects the cube and removes it savely
* @param ctxt sifteo framework param. not used.
* @param cid cube id
*/
static void onCubeDisconnect(void* ctxt, unsigned cid) {
    // mark as lost and clear from other cube sets
    lostCubes.mark(cid);
    newCubes.clear(cid);
    reconnectedCubes.clear(cid);
    dirtyCubes.clear(cid);
    activeCubes.clear(cid);
}

/** function to catch the cubeRefresh event. Marks dirty cubes
* @param ctxt sifteo framework param. not used.
* @param cid cube id
*/
static void onCubeRefresh(void* ctxt, unsigned cid) {
    // mark this cube for a future repaint
    dirtyCubes.mark(cid);
}

/** Main function. registers all events. set modes of all cubes and activates all cubes. starts the infinity loop
*/
void main() {
    // initialize asset configuration and loader
    config.append(gMainSlot, BootstrapAssets);
    loader.init();

    // subscribe to events
    Events::cubeConnect.set(onCubeConnect);
    Events::cubeDisconnect.set(onCubeDisconnect);
    Events::cubeRefresh.set(onCubeRefresh);
	Events::cubeAccelChange.set(onAccelChange);
	Events::cubeTouch.set(onTouch);
	Events::neighborAdd.set(onNeighborAdd);
    Events::neighborRemove.set(onNeighborRemove);

	vbuf[redCube].initMode(BG0_SPR_BG1);
	vbuf[redCube].attach(redCube);

	vbuf[yellowCube].initMode(BG0_SPR_BG1);
	vbuf[yellowCube].attach(yellowCube);

	vbuf[blueCube].initMode(BG0_SPR_BG1);
	vbuf[blueCube].attach(blueCube);

	vbuf[lightBulbCube].initMode(BG0_SPR_BG1);
	vbuf[lightBulbCube].attach(lightBulbCube);

    // initialize cubes
    for(CubeID cid : CubeSet::connected()) {

		//paintPotCubes[cid].init(cid);

        vbuf[cid].attach(cid);
        activateCube(cid);
    }

	//TurnLightBulbOn
	communication.LightBulbOn();
    
    // run loop
    for(;;) {
        paintWrapper();
    }
}
