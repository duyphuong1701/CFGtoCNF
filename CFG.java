package CFG;

import java.lang.reflect.Array;
import java.util.*;

public class CFG { // G ={V,T,P,S}
	protected LinkedHashSet<String> V = new LinkedHashSet<>(); // Tap ki hieu chua ket thuc
	protected LinkedHashSet<String> T = new LinkedHashSet<>(); // Tap ki hieu ket thuc
	protected Production P = new Production();
	protected String S = ""; // ki hieu bat dau

	public CFG() {
	}

	public CFG(LinkedHashSet<String> V, LinkedHashSet<String> T, Production P, String S) {
		this.V = V;
		this.T = T;
		this.P = P;
		this.S = S;
		this.T.add("#");
	}
	public void exec() {
		r1();
		r2();
		r3();
		r4();
		r5();
	}
	public CFG getCFG() {
		CFG G1 = new CFG();
		G1.V = this.V;
		G1.T = this.T;
		G1.P = this.P;
		G1.S = this.S;
		return G1;
	}

	public void update(CFG G1) {
		this.P = G1.P;
		this.S = G1.S;
		LinkedHashSet<String> T1 = new LinkedHashSet<>();
		Set<String> keySet = this.P.getKey();
		for (String k : keySet)
			for (String v : this.P.getValue(k)) {
				for (int i = 0; i < v.length(); i++) {
					String getV = String.valueOf(v.charAt(i));
					if (this.T.contains(getV)) {
						T1.add(getV);
					}
				}
			}
		this.T = T1;
		LinkedHashSet<String> V1 = new LinkedHashSet<>();
		for (String k : keySet)
			V1.add(k);
		this.V = V1;
	}

	public boolean checkStateEnd(String v, CFG G1) {
		for (int i = 0; i < v.length(); i++) {
			String r = String.valueOf(v.charAt(i));
			if (G1.T.contains(r) == false)
				return false;
		}
		return true;
	}

	public boolean checkProduction(String v, CFG G1) {
		for (int i = 0; i < v.length(); i++) {
			String r = String.valueOf(v.charAt(i));
			if (V.contains(r))
				if (!G1.V.contains(r))
					return false;
		}
		return true;
	}

	public String getNewV(String v, String comp) {
		String res = "";
		boolean flag = false;
		for (int i = 0; i < v.length(); i++) {
			String st = String.valueOf(v.charAt(i));
			if (st.equals(comp) && flag == false) {
				res = res;
				flag = true;
			} else
				res += st;
		}

		return res;
	}

	public boolean checkEp(String v, String E) {
		// kiem tra v co nam trong E
		for (int i = 0; i < v.length(); i++) {
			String st = String.valueOf(v.charAt(i));
			if (E.equals(st))
				return true;
		}
		return false;
	}

	public boolean checkEp2(String v, LinkedHashSet<String> E) {
		// kiem tra v co nam trong trong tap ket thuc
		for (int i = 0; i < v.length(); i++) {
			for (String u : E) {
				String st = String.valueOf(v.charAt(i));
				if (st.equals(u))
					return true;
			}
		}
		return false;
	}

	public boolean checkEp3(String v, LinkedHashSet<String> E) {
		// kiem tra toan chuoi v co nam trong trong tap ket thuc
		for (int i = 0; i < v.length(); i++) {
			String st = String.valueOf(v.charAt(i));
			if (!E.contains(st))
				return false;
		}
		return true;
	}

	public String getIndexEndState(String v) {
		String st = "";
		for (int i = 0; i < v.length(); i++) {
			st = String.valueOf(v.charAt(i));
			if (T.contains(st))
				break;
		}
		if (T.contains(st))
			return st;
		return null;
	}

	public String getNewState(LinkedHashSet<String> E) {
		String cc = "";
		for (int i = 65; i <= 90; i++) {
			char c = (char) i;
			cc = String.valueOf(c);
			if (!E.contains(cc))
				break;
		}
		return cc;
	}

	public String getStartState(String u, Production E) {
		Set<String> keySet = E.getKey();
		String res = "";
		for (String t : keySet) {
			for (String v : E.getValue(t))
				if (v.equals(u))
					return t;
		}
		return null;
	}

	public void r1() {
		// xu li luat sinh khong sinh ra ki hieu het thuc
		System.out.print("\n\n* Xu li xoa ki hieu ket thuc");

		LinkedHashSet<String> V1 = new LinkedHashSet<>();
		LinkedHashSet<String> T1 = new LinkedHashSet<>();
		Production P1 = new Production();
		Set<String> keySet = this.P.getKey();
		for (String k : keySet)
			for (String v : this.P.getValue(k)) {
				if (checkStateEnd(v, getCFG())) {
					V1.add(k);
					P1.addP(k, v);
				}
			}
		CFG G1 = new CFG(V1, T1, P1, "S");
		for (int fi = 0; fi < 20; fi++)
			for (String k : keySet)
				for (String v : P.getValue(k)) {
					if (!checkStateEnd(v, getCFG())) {
						if (checkProduction(v, G1)) {

							V1.add(k);
							P1.addP(k, v);
							G1 = new CFG(V1, T1, P1, "S");
						}
					}
				}
		// System.out.println(T1);
		update(G1);
	}

	public void r2() {
		// xu li luat sinh khong sinh ra tu ki hieu bat dau
		System.out.print("\n\n* Xu li xoa ki hieu khong sinh ra tu ki hieu bat dau");

		LinkedHashSet<String> V1 = new LinkedHashSet<>();
		LinkedHashSet<String> T1 = new LinkedHashSet<>();
		Production P1 = new Production();
		String S1 = this.S;
		V1.add(this.S);

		Set<String> keySet = this.P.getKey();
		for (String k : keySet)
			for (String v : this.P.getValue(k)) {
				for (int i = 0; i < v.length(); i++) {
					String v_At = String.valueOf(v.charAt(i));
					if (this.V.contains(v_At)) {
						V1.add(v_At);
						P1.addP(k, v);
					}
				}
			}
		CFG G1 = new CFG(V1, T1, P1, S1);
		for (int j = 0; j < this.V.size(); j++) {
			for (String k : keySet)
				for (String v : this.P.getValue(k)) {
					for (int i = 0; i < v.length(); i++) {
						String v_At = String.valueOf(v.charAt(i));
						if (G1.V.contains(v_At)) {
							V1.add(v_At);
							P1.addP(k, v);
							G1 = new CFG(V1, this.T, P1, this.S);
						}
					}
				}
		}
		for (String k : keySet)
			for (String v : this.P.getValue(k)) {
				if (checkStateEnd(v, getCFG()) && G1.V.contains(k)) {
					V1.add(k);
					P1.addP(k, v);
					G1 = new CFG(V1, T1, P1, S1);
				}
			}

		update(G1);
	}

	public void r3() {
		String ep = "#";
		// xu li luat sinh sinh ra epsilon
		System.out.print("\n\n* Xu li xoa ki epsilon");
		LinkedHashSet<String> V1 = new LinkedHashSet<>();
		LinkedHashSet<String> T1 = new LinkedHashSet<>();
		LinkedHashSet<String> keyFound = new LinkedHashSet<>();
		Production P1 = new Production();
		String S1 = this.S;
		V1.add(this.S);

		Set<String> keySet = this.P.getKey();
		for (String k : keySet)
			for (String v : this.P.getValue(k)) {
				if (v.equals(ep)) {
					keyFound.add(k);
				}
			}
		keyFound.add(ep);
		CFG G1 = new CFG(V1, T1, P1, S1);
		for (int i = 0; i < this.V.size(); i++) {
			for (String k : keySet) {
				for (String v : this.P.getValue(k)) {
					for (String u : keyFound) {
						if (checkEp(v, u)) {
							if (v.length() > 1) {
								String newV = getNewV(v, u);
								P1.addP(k, v);
								P1.addP(k, newV);
							}
						}
					}
				}
			}
		}
		for (String k : keySet)
			for (String v : this.P.getValue(k)) {
				if (checkEp2(v, keyFound) == false) {
					P1.addP(k, v);
				}
			}
		Production arrP[] = new Production[this.V.size() + 1];
		for (int i = 0; i <= this.V.size(); i++)
			arrP[i] = new Production();
		arrP[0] = new Production(P1);

		for (int i = 0; i < this.V.size(); i++) {
			Set<String> keyset = arrP[i].getKey();
			for (String k : keyset) {
				for (String v : arrP[i].getValue(k)) {
					for (String u : keyFound) {
						if (checkEp(v, u)) {
							if (v.length() > 1) {
								String newV = getNewV(v, u);
								arrP[i + 1].addP(k, v);
								arrP[i + 1].addP(k, newV);
							}
						}
					}
				}
			}
			for (String k : keyset)
				for (String v : arrP[i].getValue(k)) {
					if (checkEp2(v, keyFound) == false) {
						arrP[i + 1].addP(k, v);
					}
				}
		}
		G1 = new CFG(V1, T1, arrP[this.V.size()], S1);
		update(G1);
	}

	public void r4() {
		// xu li luat sinh don vi
		System.out.print("\n\n* Xu li luat sinh don vi");
		Production P2 = new Production();
		Set<String> keySet = this.P.getKey();
		for (int i = 0; i < this.V.size(); i++) {
			for (String k : keySet)
				for (String v : this.P.getValue(k)) {
					if (v.length() == 1 && this.V.contains(v)) {
						for (String state : this.P.getValue(v)) {
							P2.addP(k, state);
						}
					}
				}
		}
		for (String k : keySet)
			for (String v : this.P.getValue(k)) {
				if (v.length() == 1 && this.V.contains(v))
					continue;
				P2.addP(k, v);
			}
		CFG G1 = new CFG(this.V, this.T, P2, this.S);
		update(G1);
	}

	public void r5() {
		// xu li luat sinh >1 chua ki hieu ket thuc
		System.out.print("\n\n* Xu li chomsky");
		LinkedHashSet<String> V1 = new LinkedHashSet<>();
		LinkedHashSet<String> listRemove = new LinkedHashSet<>();
		V1 = this.V;
		Production P1 = new Production();
		Production P2 = new Production();
		Set<String> keySet = this.P.getKey();

		// xu li luat sinh chua ky tu ket thuc >1
		for (String k : keySet)
			for (String v : this.P.getValue(k)) {
				if (!checkEp3(v, this.T)) {
					if (v.length() > 1 && getIndexEndState(v) != null) {
						String str = v;
						char oldc = getIndexEndState(v).charAt(0);
						char newc = getNewState(V1).charAt(0);
						if (getStartState(String.valueOf(oldc), P1) != null)
							newc = getStartState(String.valueOf(oldc), P1).charAt(0);
						str = str.replace(oldc, newc);
						P1.addP(k, str);
						P1.addP(String.valueOf(newc), String.valueOf(oldc));
						V1.add(String.valueOf(newc));
						listRemove.add(v);
					}
				}
			}

		// them luat sinh con loai vao
		for (String k : keySet)
			for (String v : this.P.getValue(k)) {
				if (!listRemove.contains(v))
					P1.addP(k, v);
			}
		CFG G1 = new CFG(this.V, this.T, P1, this.S);
		update(G1);
		// xu li luat sinh chua ket thuc >2
		listRemove.clear();
		V1.clear();
		V1 = this.V;
		// for (int i = 0; i < this.V.size(); i++) {
		{
			for (String k : keySet)
				for (String v : this.P.getValue(k)) {
					if (checkEp3(v, this.V) && v.length() > 2) {
						String str = v;
						String oldstr = str.substring(0, 2);
						String newstr = getNewState(V1);
						if (getStartState(oldstr, P2) != null)
							newstr = getStartState(oldstr, P2);

						str = str.replace(oldstr, newstr);
						P2.addP(k, str);
						P2.addP(newstr, oldstr);
						V1.add(newstr);
						listRemove.add(v);
					}
				}
		}
		// them luat sinh con loai vao
		Set<String> keyset = this.P.getKey();
		for (String k : keyset)
			for (String v : this.P.getValue(k)) {
				if (!listRemove.contains(v))
					P2.addP(k, v);
			}
		G1 = new CFG(this.V, this.T, P2, this.S);
		update(G1);
	//	this.P.print();
	}

	// **// Phan thao tac xu li nhap xuat

	public void printG() {
		System.out.print("\nTap ki tu chua ket thuc: " + V);
		System.out.print("\nTap ki tu ket thuc     : " + T);
		System.out.print("\nTap luat sinh          : ");
		P.print();
		System.out.print("\nKi hieu bat dau        : " + S);
	}

	public static void main(String[] args) {
		String[] V = { "S", "A", "B", "C", "D" };
		String[] T = { "a", "b", };
		LinkedHashSet<String> V1 = new LinkedHashSet<>(Arrays.asList(V));
		LinkedHashSet<String> T1 = new LinkedHashSet<>(Arrays.asList(T));
		Production P1 = new Production();
		P1.addP("S", "ABC");
		P1.addP("A", "BB");
		P1.addP("A", "#");
		P1.addP("A", "aBB");
		P1.addP("B", "CC");
		P1.addP("B", "a");
		P1.addP("C", "AA");
		P1.addP("C", "b");
		P1.addP("C", "ab");
		P1.addP("C", "bAA");
		String S1 = "S";
		CFG G = new CFG(V1, T1, P1, S1);
		G.exec();
		G.printG();
	}
}
