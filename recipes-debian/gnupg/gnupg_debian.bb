# base recipe: meta/recipes-support/gnupg/gnupg_2.2.13.bb
# base branch: warrior
# base commit: 5da65baa4ab108581d3e1696954ad49e36184a4d

SUMMARY = "GNU Privacy Guard - encryption and signing tools (2.x)"
HOMEPAGE = "http://www.gnupg.org/"
LICENSE = "GPLv3 & LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=189af8afca6d6075ba6c9e0aa8077626 \
                    file://COPYING.LGPL3;md5=a2b6bf2cb38ee52619e60f30a1fc7257"

DEPENDS = "npth libassuan libksba zlib bzip2 readline libgcrypt"

inherit debian-package
require recipes-debian/sources/gnupg2.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-support/gnupg/gnupg"

inherit autotools gettext texinfo pkgconfig

SRC_URI += " \
           file://0001-Use-pkg-config-to-find-pth-instead-of-pth-config.patch \
           file://0002-use-pkgconfig-instead-of-npth-config.patch \
           file://0003-dirmngr-uses-libgpg-error.patch \
           file://0001-Woverride-init-is-not-needed-with-gcc-9.patch \
           "
SRC_URI_append_class-native = " file://0001-configure.ac-use-a-custom-value-for-the-location-of-.patch \
                                file://relocate.patch"


EXTRA_OECONF = "--disable-ldap \
		--disable-ccid-driver \
		--with-zlib=${STAGING_LIBDIR}/.. \
		--with-bzip2=${STAGING_LIBDIR}/.. \
		--with-readline=${STAGING_LIBDIR}/.. \
		--enable-gpg-is-gpg2 \
               "
RRECOMMENDS_${PN} = "pinentry"

do_configure_prepend () {
	# Else these could be used in prefernce to those in aclocal-copy
	rm -f ${S}/m4/gpg-error.m4
	rm -f ${S}/m4/libassuan.m4
	rm -f ${S}/m4/ksba.m4
	rm -f ${S}/m4/libgcrypt.m4
}

do_install_append() {
	ln -sf gpg2 ${D}${bindir}/gpg
	ln -sf gpgv2 ${D}${bindir}/gpgv
}

do_install_append_class-native() {
	create_wrapper ${D}${bindir}/gpg2 GNUPG_BINDIR=${STAGING_BINDIR_NATIVE}
}

PACKAGECONFIG ??= "gnutls"
PACKAGECONFIG[gnutls] = "--enable-gnutls, --disable-gnutls, gnutls"
PACKAGECONFIG[sqlite3] = "--enable-sqlite, --disable-sqlite, sqlite3"

BBCLASSEXTEND = "native"
