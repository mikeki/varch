from django.conf.urls.defaults import patterns, include, url

import webservice.views

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    url(r'^compare$', webservice.views.compare),
    # Examples:
    # url(r'^$', 'varch_algorithms.views.home', name='home'),
    # url(r'^varch_algorithms/', include('varch_algorithms.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # url(r'^admin/', include(admin.site.urls)),
)
