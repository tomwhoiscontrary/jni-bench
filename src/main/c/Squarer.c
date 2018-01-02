#include <li_earth_urchin_twic_Squarer.h>

JNIEXPORT jint JNICALL Java_li_earth_urchin_twic_Squarer_squareNatively(JNIEnv *env, jclass klass, jint x) {
    return x * x;
}
