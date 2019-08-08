package fr.vilia.twx;

import com.thingworx.data.util.InfoTableInstanceFactory;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.resources.Resource;
import com.thingworx.types.InfoTable;

import java.util.List;

public class WindowsServices extends Resource {

	private static final long serialVersionUID = 1L;

	@ThingworxServiceDefinition(name = "QueryServices", description = "Returns all services")
	@ThingworxServiceResult(name = "result", description = "Result", baseType = "INFOTABLE", aspects = { "dataShape:WindowsService" })
	public InfoTable QueryServices() throws Exception {
		InfoTable result = null;
		try {
			result = InfoTableInstanceFactory.createInfoTableFromDataShape("WindowsService");
		} catch (Exception ignore) {
			// Can safely ignore it here, because we bundle this data shape together with the code
			ignore.printStackTrace();
		}

		// Convert to INFOTABLE
		List<ServiceDefinition> services = WindowsServiceParser.execute();
		for (ServiceDefinition s: services) {
			result.addRow(s.toValueCollection());
		}

		return result;
	}

}
