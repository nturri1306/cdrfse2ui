package it.unidoc.cdr.core.ui.conf;

import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author b.amoruso
 */

@ConfigurationProperties(prefix = "cdr.ui")
public class CdrUiConfiguration {

    private File usersFile;
    private File cacheDirectory;
    private boolean fseBrokerEnabled;
    @Value("${spring.servlet.multipart.max-file-size}")
    private String fseBrokerMaxUploadSize;




    private String fseBrokerBaseUrl;
    private String fseBrokerUsername;
    private String fseBrokerPassword;
    // SSL/TLS
    @Value("${server.ssl.enabled}")
    private boolean secure;
    @Value("${server.ssl.trust-store}")
    // Resource can be classpath:, file: or url:
    private Resource trustStore;
    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;
    // XDS
    private boolean xdsEnabled;
    private String xdsQueryUrl;
    private String xdsMpqUrl;
    private List<String> xdsRetrieveUrl;
    private String xdsSubmitUrl;
    private String restBaseUrl;
    private String restUsername;
    private String restPassword;



    public static File makeDirectory(File dir) {
        if (!dir.exists())
            dir.mkdirs();

        return dir;
    }

    public File getUsersFile() {
        File parent = usersFile.getParentFile();
        if (!parent.exists())
            parent.mkdirs();

        return usersFile;
    }

    public void setUsersFile(File file) {
        this.usersFile = file;
    }

    public File getCacheDirectory() {
        return makeDirectory(cacheDirectory);
    }

    public void setCacheDirectory(File cacheDirectory) {
        this.cacheDirectory = cacheDirectory;
    }

    public boolean isFseBrokerEnabled() {
        return fseBrokerEnabled;
    }

    public void setFseBrokerEnabled(boolean fseBrokerEnabled) {
        this.fseBrokerEnabled = fseBrokerEnabled;
    }

    public boolean isXdsEnabled() {
        return xdsEnabled;
    }

    public void setXdsEnabled(boolean xdsEnabled) {
        this.xdsEnabled = xdsEnabled;
    }

    public String getXdsQueryUrl() {
        return xdsQueryUrl;
    }

    public void setXdsQueryUrl(String xdsQueryUrl) {
        this.xdsQueryUrl = xdsQueryUrl;
    }

    public String getXdsMpqUrl() {
        return xdsMpqUrl;
    }

    public void setXdsMpqUrl(String xdsMpqUrl) {
        this.xdsMpqUrl = xdsMpqUrl;
    }

    public List<String> getXdsRetrieveUrl() {
        if (xdsRetrieveUrl == null)
            xdsRetrieveUrl = new ArrayList<>();

        return xdsRetrieveUrl;
    }

    public void setXdsRetrieveUrl(List<String> items) {
        this.xdsRetrieveUrl = items;
    }

    public String getXdsRetrieveUrl(String oid) {
        String[] item = getXdsRetrieveUrl().stream().map(
                        t -> t.split(",")).filter(s -> s[0].equals(oid)).
                findFirst().orElse(null);

        return item != null && item.length > 1 ? item[1] : null;
    }

    public String getXdsSubmitUrl() {
        return xdsSubmitUrl;
    }

    public void setXdsSubmitUrl(String xdsSubmitUrl) {
        this.xdsSubmitUrl = xdsSubmitUrl;
    }

    public String getFseBrokerMaxUploadSize() {
        return fseBrokerMaxUploadSize;
    }

    public void setFseBrokerMaxUploadSize(String fseBrokerMaxUploadSize) {
        this.fseBrokerMaxUploadSize = fseBrokerMaxUploadSize;
    }


    public String getFseBrokerBaseUrl() {
        return fseBrokerBaseUrl;
    }

    public void setFseBrokerBaseUrl(String fseBrokerBaseUrl) {
        this.fseBrokerBaseUrl = fseBrokerBaseUrl;
    }

    public String getFseBrokerUsername() {
        return fseBrokerUsername;
    }

    public void setFseBrokerUsername(String fseBrokerUsername) {
        this.fseBrokerUsername = fseBrokerUsername;
    }

    public String getFseBrokerPassword() {
        return fseBrokerPassword;
    }

    public void setFseBrokerPassword(String fseBrokerPassword) {
        this.fseBrokerPassword = fseBrokerPassword;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public Resource getTrustStore() {
        return trustStore;
    }

    public void setTrustStore(Resource trustStore) {
        this.trustStore = trustStore;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }


    public String getRestBaseUrl() {
        return restBaseUrl;
    }

    public void setRestBaseUrl(String restBaseUrl) {
        this.restBaseUrl = restBaseUrl;
    }

    public String getRestUsername() {
        return restUsername;
    }

    public void setRestUsername(String restUsername) {
        this.restUsername = restUsername;
    }

    public String getRestPassword() {
        return restPassword;
    }

    public void setRestPassword(String restPassword) {
        this.restPassword = restPassword;
    }

    public SSLContext getSSLContext() throws Exception {
        if (isSecure())
            return SSLContextBuilder.create().loadTrustMaterial(
                            getTrustStore().getURL(), getTrustStorePassword().toCharArray()).
                    build();

        return null;
    }

}
