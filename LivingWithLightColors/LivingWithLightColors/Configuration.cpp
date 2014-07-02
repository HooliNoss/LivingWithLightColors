#include <sifteo.h>
#include "assets.gen.h"

using namespace Sifteo;

// METADATA
static Metadata M = Metadata()
    .title("Living with light colors")
    .package("ch.hslu.livingwithlightcolors", "1.0")
    .icon(Icon)
    .cubeRange(1, CUBE_ALLOCATION);


AssetSlot gMainSlot = AssetSlot::allocate()
    .bootstrap(BootstrapAssets);

// GLOBALS
static VideoBuffer vbuf[CUBE_ALLOCATION]; // one video-buffer per cube
static CubeSet newCubes; // new cubes as a result of paint()
static CubeSet lostCubes; // lost cubes as a result of paint()
static CubeSet reconnectedCubes; // reconnected (lost->new) cubes as a result of paint()
static CubeSet dirtyCubes; // dirty cubes as a result of paint()
static CubeSet activeCubes; // cubes showing the active scene

static TiltShakeRecognizer motion[CUBE_ALLOCATION];

static AssetLoader loader; // global asset loader (each cube will have symmetric assets)
static AssetConfiguration<1> config; // global asset configuration (will just hold the bootstrap group)

enum Color {Red, Yellow, Blue, Black};
enum TiltDirection {
	XPositivLow,
	XPositivModerate,
	XPositivHigh,
	XNegativLow,
	XNegativModerate,
	XNegativHigh,
	YPositivLow,
	YPositivModerate,
	YPositivHigh,
	YNegativLow,
	YNegativModerate,
	YNegativHigh,
	ZPositivLow,
	ZPositivModerate,
	ZPositivHigh,
	ZNegativLow,
	ZNegativModerate,
	ZNegativHigh,
	NoTilting};

int cubeCounter = 0;
int cAccelDeltaContainerMin = 500;
int cAccelDeltaLightBulbMin = 200;
int cAccelDeltaPaintPotMin = 15;
int cClickDeltaMin = 750;

enum CubeType 
{
	containerCube, 
	redCube,
	yellowCube, 
	blueCube,
	lightBulbCube
};

enum BackgroundImages
{
	BgLightBulbOff,
	BgLightBulb0,
	BgLightBulb1,
	BgLightBulb2,
	BgLightBulb3,
	BgLightBulb4,
	BgLightBulb5,
	BgLightBulb6,
	BgRed1,
	BgRed2,
	BgRed3,
	BgRed4,
	BgRed5,
	BgRed6,
	BgRed7,
	BgRed8,
	BgRed9,
	BgRed10,
	BgYellow1,
	BgYellow2,
	BgYellow3,
	BgYellow4,
	BgYellow5,
	BgYellow6,
	BgYellow7,
	BgYellow8,
	BgYellow9,
	BgYellow10,
	BgBlue1,
	BgBlue2,
	BgBlue3,
	BgBlue4,
	BgBlue5,
	BgBlue6,
	BgBlue7,
	BgBlue8,
	BgBlue9,
	BgBlue10,
	BgEmptyContainer
};

enum FillDirection
{
	FillDirectionAdd,
	FillDirectionRemove
};

enum LightBulbState
{
	LightBulbStateOn,
	LightBulbStateOff
};


/**
* Includes all internal configuration properties
*/
class Configuration
{
	public:

	private:


}tConfiguration;