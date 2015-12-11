import java.io.*;
import java.net.*;
import java.util.*;

class cdata implements Runnable
{
	Thread t;
	URL url;
	cdata(URL url){
		this.url=url;
		t=new Thread(this);
		t.start();
	}
	public void run(){
		try{
		spider s = new spider(url);
		}
		catch(Exception e){}
	}
}
class spider {
	public static HashSet<URL> _list = new HashSet<URL>();
	spider(URL u1) throws Exception{
		InputStream in=u1.openStream();
		String output=null;
		String str_data=null;
		int i;
		while((i=in.read())!=-1){
			str_data=str_data+(char)i;
		}
		String token[]=str_data.split(" ");
		for(String t:token){
			if(t.startsWith("href=")&&!t.startsWith("href=\"javascript")&&!t.endsWith(".ico\"")&&!t.endsWith(".css\"")&&!t.endsWith(".pdf\"")){
				t=t.split("\"")[1];
				String[] splitdata = t.split("\"");
				for(String ti:splitdata){
					if(ti.startsWith("http")){
						output=ti;
					}
					else if(ti.startsWith("/")){
						output=u1.getProtocol()+"://"+u1.getHost()+ti;
					}
					else{
						output=u1.getProtocol()+"://"+u1.getHost()+"/"+t;
					}
				}
				if(!_list.contains(new URL(output)) && output!=null){
					_list.add(new URL(output));
					System.out.println(output);
					new cdata(new URL(output));
				}
			}
		}
	}
	public static void main(String args[]) throws Exception{
		System.out.print("Enter URL: ");
		Scanner scn=new Scanner(System.in);
		String str1=new String();
		str1=scn.nextLine();
		URL url=new URL(str1);
		new cdata(url);
	}
}
