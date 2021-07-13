package CFG;

import java.security.KeyStore.Entry;
import java.util.*;

public class Production {
	protected Map<String, LinkedHashSet<String>> rule = new LinkedHashMap<>();

	public Production() {
	}

	public Production(String key, String value) {
		LinkedHashSet<String> v = new LinkedHashSet<String>();
		if (rule.containsKey(key))
			v = rule.get(key); // tao tap v luu lai cac luat sinh cu neu da co luat sinh chua ki hieu chua ket
								// thuc
		v.add(value);
		rule.put(key, v);
	}
	public Production(Production E) {
		this.rule = E.rule;
	}
	public Production copyP() {
		return this;
	}
	public Set<String> getKey(){
		return rule.keySet();
	}
	public LinkedHashSet<String> getValue(String key){
		return rule.get(key);
	}
	public void addP(String key, String value) {
		LinkedHashSet<String> v = new LinkedHashSet<String>();
		if (rule.containsKey(key))
			v = rule.get(key); // tao tap v luu lai cac luat sinh cu neu da co luat sinh chua ki hieu chua ket
								// thuc
		v.add(value);
		rule.put(key, v);
	}
	
	public void print() {
		String enter="";
		Set<String> keySet = rule.keySet();
		for (String k : keySet) {
			for (String v : rule.get(k)) {
				if(enter!=k) {
					System.out.print("\n\t" + k + "-> " + v);
					enter = k;
				}
				else System.out.print(" | " + v);
			}
		}
	}
}
