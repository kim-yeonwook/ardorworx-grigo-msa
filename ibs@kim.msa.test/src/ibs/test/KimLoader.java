package ibs.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import ibs.test.bus.AdvertizedMosTest;
import ibs.test.bus.RequestMosTest;
import ibs.test.bus.ServiceMosTest;
import ibs.test.edge.Edge;
import mecury.io.LocalProperties;
import v3.venus.mod.Modular;
import v3.venus.route.ADVAction;
import v3.venus.route.ADVRouter;
import v3.venus.route.AdvertizedBus;
import v3.venus.route.ReqRouter;
import v3.venus.route.RequestBus;
import v3.venus.route.ServiceBus;
import v3.venus.route.SvcRouter;
import v3.venus.route._ADVAction;
import v3.venus.route._ADVBus;
import v3.venus.route._REQBus;
import v3.venus.route._SVCBus;
import venus._BootStrap;
import xen.mecury.JdbcProperties;
import xen.mecury.Xen;
import xen.mecury.XenContext;


@_BootStrap()
public class KimLoader extends Modular {

	protected Xen xen;

	@Override
	public void hello() throws Exception {

		xen = new Xen();
		xen.hello(setDBPool());
		Edge.setEdge();
		
		super.hello();
		
		for(Edge e:Edge.edge_list.values()) {
			ADVRouter.pub("ADV/edge/" + e.id +"/reset", new byte[] {});
		}
		
	}
	
	@Override
	protected void service() throws Exception {
		if(null==svcBus) {
			svcBus = new ServiceMosTest();
		}
		svcBus.setTask(this, tasks);
		SvcRouter.open(svcBus);
	}

	@Override
	protected void request() throws Exception {
		if(null==this.requestBus) {
			requestBus = new RequestMosTest(); 
		}
		requestBus.setTask(this, tasks);
		ReqRouter.open(requestBus);
	}
	
	@Override
	protected void advertized() throws Exception {
		if(null==advBus) {
			advBus = new AdvertizedMosTest(); 
		}
		ADVRouter.open(advBus,tasks, advAction);
	}
	
	
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void setDir(String path) throws Exception {

		File[] files = new File(path).listFiles();
		for(File file:files) {

			if(file.isDirectory()) {
				setDir(file.getAbsolutePath());
			} else if(file.getName().endsWith(".class")){
				String cName = file.getAbsolutePath().replace(abs+"bin"+File.separator, "").replace(abs+"classes"+File.separator, "").replace(".class", "").replace(File.separator.charAt(0), '.');
				System.out.println(cName);
				try {
					Class mc = this.getClass().getClassLoader().loadClass(cName);
					if(mc.isAnnotationPresent(_ADVBus.class)) {
						advBus = (AdvertizedBus)mc.newInstance();
						
					} else if(mc.isAnnotationPresent(_REQBus.class)) {
						requestBus = (RequestBus)mc.newInstance();
						
					} else if(mc.isAnnotationPresent(_SVCBus.class)) {
						svcBus = (ServiceBus)mc.newInstance();
						
					} else if(mc.isAnnotationPresent(_ADVAction.class)) {
						if(null==this.advAction) {
							advAction = new HashMap<String,AdvertizedBus.Callback>();
						}
						ADVAction action = (ADVAction)mc.newInstance();
						advAction.put(action.topic(), action.action());
						
					}
					
					tasks.regTask(mc);
				}catch(Throwable e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}
	
	
	
	protected XenContext setDBPool() {
		
		XenContext xCtx = new XenContext();
		xCtx.refreshTime = (LocalProperties.getInt("db_refresh.time")!=0?LocalProperties.getInt("db_refresh.time"):1000*60*60);
		xCtx.properties = new ArrayList<JdbcProperties>();
		
		JdbcProperties prop = new JdbcProperties();
		prop.poolName = LocalProperties.get("v1.db.pool.name");
		prop.className = LocalProperties.get("db.class.name");
		prop.driverName = LocalProperties.get("db.driver.name");
		prop.urlName = LocalProperties.get("db.url.name");
		prop.userName = LocalProperties.get("db.user.name");
		prop.password = LocalProperties.get("db.password");
		prop.initialCount = LocalProperties.getInt("db.init.count");
		prop.maxCount = LocalProperties.getInt("db.max.count");
		prop.validationQuery = LocalProperties.get("db.validation.query");
		xCtx.properties.add(prop);

		
		return xCtx;
	}
	
	
	@SuppressWarnings("unused")
	private static final class _test {
		public static void main(String[] args) {
			try {
				
				new KimLoader().hello();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
