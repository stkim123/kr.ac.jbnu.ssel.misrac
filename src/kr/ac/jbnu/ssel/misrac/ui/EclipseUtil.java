package kr.ac.jbnu.ssel.misrac.ui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * 
 * @author Suntae Kim
 *
 */
public class EclipseUtil
{

	private static final String pluginID = "kr.ac.jbnu.ssel.misrac";

	/**
	 * TODO: Not implemented Yet. Donot USE! 
	 * 
	 * relative data path from root of a plugin. e.g., "files/test.txt",
	 * "Resource/rules.xml"
	 * 
	 * @param relativeResourcePath
	 * @return
	 */
	public static File loadResource(String relativeResourcePath) throws Exception
	{
		Bundle bundle = Platform.getBundle(pluginID);
		URL fileURL = bundle.getEntry(relativeResourcePath);
		return new File(FileLocator.resolve(fileURL).toURI());
	}
}
