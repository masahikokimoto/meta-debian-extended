MAJOR_VERSION = "2.32"

require ${COREBASE}/meta/recipes-core/util-linux/util-linux.inc

# override LIC_FILES_CHKSUM
LIC_FILES_CHKSUM = "file://README.licensing;md5=972a134f1e14b2b060e365df2fab0099 \
                    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://Documentation/licenses/COPYING.GPL-2.0-or-later;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://Documentation/licenses/COPYING.LGPL-2.1-or-later;md5=4fbd65380cdd255951079008b364516c \
                    file://Documentation/licenses/COPYING.BSD-3-Clause;md5=58dcd8452651fc8b07d1f65ce07ca8af \
                    file://Documentation/licenses/COPYING.BSD-4-Clause-UC;md5=263860f8968d8bafa5392cab74285262 \
                    file://libuuid/COPYING;md5=6d2cafc999feb2c2de84d4d24b23290c \
                    file://libmount/COPYING;md5=7c7e39fb7d70ffe5d693a643e29987c2 \
                    file://libblkid/COPYING;md5=693bcbbe16d3a4a4b37bc906bc01cc04"

# clean SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/util-linux.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-core/util-linux/util-linux"
DEBIAN_QUILT_PATCHES = ""

# To support older hosts, we need to patch and/or revert
# some upstream changes.  Only do this for native packages.
OLDHOST = ""
OLDHOST_class-native = "file://util-linux-native-qsort.patch"

SRC_URI += "file://configure-sbindir.patch \
            file://runuser.pamd \
            file://runuser-l.pamd \
            ${OLDHOST} \
            file://ptest.patch \
            file://run-ptest \
            file://display_testname_for_subtest.patch \
            file://avoid_parallel_tests.patch \
"

CACHED_CONFIGUREVARS += "scanf_cv_alloc_modifier=ms"

EXTRA_OECONF_class-native = "${SHARED_EXTRA_OECONF} \
			     --disable-use-tty-group \
"
EXTRA_OECONF_class-nativesdk = "${SHARED_EXTRA_OECONF} \
				--disable-use-tty-group \
"
