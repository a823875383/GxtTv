package com.jsqix.gxt.tv.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityCodeJPUtils {

	private static String STAT_STR = "JIQ|黔江机场|qj|qianjiang|黔江@RKZ|日喀则机场|rkz|rikaze|日喀则@AQG|安庆机场|aq|anqing|安庆@BFU|蚌埠机场|bb|bangbu|蚌埠@FUG|阜阳西关机场|fy|fuyang|阜阳@HFE|合肥机场|hf|hefei|合肥@TXN|黄山屯溪机场|hs|huangshan|黄山@PEK|北京首都机场|bj|beijing|北京@NAY|北京南苑机场|bj|beijing|北京@FOC|福州长乐机场|fz|fuzhou|福州@LCX|龙岩连城机场|ly|longyan|龙岩@WUS|武夷山机场|wys|wuyishan|武夷山@JJN|泉州晋江机场|qz|quanzhou|泉州@SHS|沙市机场|ss|shashi|沙市@XMN|厦门高崎机场|sm|shamen|厦门@JGN|嘉峪关机场|jyg|jiayuguan|嘉峪关@DNH|敦煌机场|dh|dunhuang|敦煌@CHW|酒泉机场|jq|jiuquan|酒泉@LHW|兰州中川机场|lz|lanzhou|兰州@IQN|庆阳西峰镇机场|qy|qingyang|庆阳@THQ|天水机场|ts|tianshui|天水@FUO|佛山机场|fs|foshan|佛山@CAN|广州白云机场|gz|guangzhou|广州@HUZ|惠州机场|hz|huizhou|惠州@SWA|揭阳潮汕机场|jy|jieyang|揭阳@MXZ|梅县机场|mx|meixian|梅县@SZX|深圳宝安机场|sz|shenzhen|深圳@ZHA|湛江机场|zj|zhanjiang|湛江@ZUH|珠海三灶机场|zh|zhuhai|珠海@AEB|百色机场|bs|baise|百色@BHY|北海福城机场|bh|beihai|北海@KWL|桂林两江机场|gl|guilin|桂林@LZH|柳州白莲机场|lz|liuzhou|柳州@NNG|南宁吴圩机场|nn|nanning|南宁@WUZ|梧州机场|wz|wuzhou|梧州@AVA|安顺机场|as|anshun|安顺@KWE|贵阳龙洞堡机场|gy|guiyang|贵阳@HZH|黎平机场|lp|liping|黎平@ACX|兴义机场|xy|xingyi|兴义@TEN|铜仁大兴机场|tr|tongren|铜仁@ZYI|遵义机场|zy|zunyi|遵义@JIC|金昌金川机场|jc|jinchang|金昌@HAK|海口美兰机场|hk|haikou|海口@SYX|三亚凤凰机场|sy|sanya|三亚@HDG|邯郸机场|hd|handan|邯郸@SHP|秦皇岛机场|qhd|qinhuangdao|秦皇岛@SJW|石家庄正定机场|sjz|shijiazhuang|石家庄@TVS|唐山机场|ts|tangshan|唐山@XNT|邢台机场|xt|xingtai|邢台@ERL|二连浩特机场|elht|erlianhaote|二连浩特@LLB|荔波机场|lb|libo|荔波@LYA|洛阳北郊机场|ly|luoyang|洛阳@NNY|南阳姜营机场|ny|nanyang|南阳@CGO|郑州新郑机场|zz|zhengzhou|郑州@DQA|大庆机场|dq|daqing|大庆@OHE|漠河机场|mh|mohe|漠河@HRB|哈尔滨太平机场|heb|haerbin|哈尔滨@HEK|黑河机场|hh|heihe|黑河@JXA|鸡西机场|jx|jixi|鸡西@JMU|佳木斯东郊机场|jms|jiamusi|佳木斯@MDG|牡丹江海浪机场|mdj|mudanjiang|牡丹江@NDG|三家子机场|qqhe|qiqihaer|齐齐哈尔@LDS|伊春机场|yc|yichun|伊春@ENH|恩施许家坪机场|es|enshi|恩施@WUH|武汉天河机场|wh|wuhan|武汉@XFN|襄樊刘集机场|xf|xiangfan|襄樊@YIH|宜昌三峡机场|yc|yichang|宜昌@CGD|常德桃花机场|cd|changde|常德@CSX|长沙黄花机场|zs|zhangsha|长沙@HNY|衡阳机场|hy|hengyang|衡阳@HJJ|怀化芷江机场|hh|huaihua|怀化@LLF|永州零陵机场|yzll|yongzhoulingling|永州零陵@DYG|张家界荷花机场|zjj|zhangjiajie|张家界@CGQ|长春龙嘉机场|zc|zhangchun|长春@JIL|吉林二台子机场|jl|jilin|吉林@TNH|通化机场|th|tonghua|通化@YNJ|延吉朝阳川机场|yj|yanji|延吉@CZX|常州奔牛机场|cz|changzhou|常州@HIA|淮安涟水机场|ha|huaian|淮安@LYG|白塔埠机场|lyg|lianyungang|连云港@NKG|南京禄口机场|nj|nanjing|南京@NTG|南通兴东机场|nt|nantong|南通@SZV|苏州光福机场|sz|suzhou|苏州@WUX|无锡机场|wx|wuxi|无锡@XUZ|徐州观音机场|xz|xuzhou|徐州@YNZ|盐城机场|yc|yancheng|盐城@KOW|赣州黄金机场|gz|ganzhou|赣州@KNC|吉安机场|ja|jian|吉安@JGS|井冈山机场|jgs|jinggangshan|井冈山@JDZ|景德镇罗家机场|jdz|jingdezhen|景德镇@JIU|九江庐山机场|jj|jiujiang|九江@KHN|南昌昌北机场|nc|nanchang|南昌@AOG|鞍山机场|as|anshan|鞍山@CHG|朝阳机场|cy|chaoyang|朝阳@DLC|大连周水子机场|dl|dalian|大连@CNI|大长山岛机场|zh|zhanghai|长海@PNJ|沙河口机场|shk|shahekou|沙河口@DDG|丹东浪头机场|dd|dandong|丹东@XEN|兴城机场|xc|xingcheng|兴城@JNZ|锦州小岭子机场|jz|jinzhou|锦州@SHE|沈阳桃仙机场|sy|shenyang|沈阳@RLK|巴彦淖尔天吉泰机场|bynes|bayannaoershi|巴彦淖尔市@BAV|包头机场|bt|baotou|包头@CIF|赤峰土城子机场|cf|chifeng|赤峰@DSN|伊金霍洛机场|eeds|eerduosi|鄂尔多斯@HET|白塔机场|hhht|huhehaote|呼和浩特@HLD|海拉尔东山机场|hle|hailaer|海拉尔@NZH|满洲里西郊机场|mzl|manzhouli|满洲里@TGO|通辽机场|tl|tongliao|通辽@WUA|乌海机场|wh|wuhai|乌海@XIL|锡林浩特机场|xlht|xilinhaote|锡林浩特@HLH|乌兰浩特机场|wlht|wulanhaote|乌兰浩特@INC|银川河东机场|yc|yinchuan|银川@GOQ|格尔木机场|gem|geermu|格尔木@XNN|西宁曹家堡机场|xn|xining|西宁@YUS|玉树机场|ys|yushu|玉树@DOY|东营机场|dy|dongying|东营@TNA|济南遥墙机场|jn|jinan|济南@JNG|济宁机场|jn|jining|济宁@LYI|临沂机场|ly|linyi|临沂@TAO|青岛流亭机场|qd|qingdao|青岛@WEH|威海大水泊机场|wh|weihai|威海@WEF|潍坊南苑机场|wf|weifang|潍坊@YNT|烟台莱山机场|yt|yantai|烟台@CIH|长治王村机场|zz|zhangzhi|长治@DAT|大同机场|dt|datong|大同@TYN|太原武宿机场|ty|taiyuan|太原@YCU|运城机场|yc|yuncheng|运城@AKA|安康五里铺机场|ak|ankang|安康@HZG|汉中西关机场|hz|hanzhong|汉中@XIY|西安咸阳机场|xa|xian|西安@ENY|二十里铺机场|ya|yanan|延安@UYN|榆林西沙机场|yl|yulin|榆林@SHA|上海虹桥机场|sh|shanghai|上海@PVG|上海浦东机场|sh|shanghai|上海@JZH|九寨沟黄龙机场|jzg|jiuzhaigou|九寨沟@CTU|成都双流机场|cd|chengdou|成都@DAX|达县河市霸机场|dx|daxian|达县@GHN|广汉机场|gh|guanghan|广汉@KGT|康定机场|kd|kangding|康定@GYS|广元|gy|guangyuan|广元@XIC|西昌机场|xc|xichang|西昌@MIG|绵阳南郊机场|my|mianyang|绵阳@NAO|南充都尉坝机场|nc|nanchong|南充@PZI|攀枝花机场|pzh|panzhihua|攀枝花@YBP|宜宾菜坝机场|yb|yibin|宜宾@LZO|泸州萱田机场|lz|luzhou|泸州@TSN|天津滨海机场|tj|tianjin|天津@BPX|昌都马草机场|cd|changdou|昌都@LXA|拉萨贡嘎机场|ls|lasa|拉萨@LZY|林芝机场|lz|linzhi|林芝@AKU|阿克苏温宿机场|aks|akesu|阿克苏@KCA|库车机场|kc|kuche|库车@KRL|库尔勒机场|kel|kuerle|库尔勒@IQM|且末机场|qm|qiemo|且末@BPL|博乐机场|betlmgzzz|boertalamengguzizhizhou|博尔塔拉蒙古自治州@HMI|哈密机场|hm|hami|哈密@HTN|和田机场|ht|hetian|和田@KHG|喀什机场|ks|kashen|喀什@KRY|克拉玛依机场|klmy|kelamayi|克拉玛依@SXJ|鄯善机场|ss|shanshan|鄯善@TLQ|吐鲁番机场|tlf|tulufan|吐鲁番@URC|地窝铺机场|wlmq|wulumuqi|乌鲁木齐@YIN|伊宁机场|yn|yining|伊宁@NLT|那拉提机场|nlt|neilati|那拉提@BSD|保山机场|bs|baoshan|保山@TCZ|腾冲机场|tc|tengchong|腾冲@YUA|元谋机场|ym|yuanmou|元谋@DLU|大理机场|dl|dali|大理@NGQ|阿里昆莎机场|al|ali|阿里@LUM|芒市机场|ms|mangshi|芒市@DIG|迪庆机场|dq|diqing|迪庆@KMG|昆明巫家坝机场|km|kunming|昆明@LJG|丽江机场|lj|lijiang|丽江@LNJ|临沧机场|lc|lincang|临沧@SYM|思茅机场|sm|simao|思茅@PUR|普洱机场|pe|puer|普洱@WNH|文山机场|ws|wenshan|文山@JHG|景洪机场|xsbn|xishuangbanna|西双版纳@ZAT|昭通机场|zt|zhaotong|昭通@HGH|杭州萧山机场|hz|hangzhou|杭州@YIW|义乌机场|yw|yiwu|义乌@NGB|宁波栎社机场|nb|ningbo|宁波@HYN|黄岩路桥机场|hy|huangyan|黄岩@WNZ|温州永强机场|wz|wenzhou|温州@HSN|舟山普陀山机场|zs|zhoushan|舟山@JUZ|衢州机场|qz|quzhou|衢州@CKG|重庆江北机场|zq|zhongqing|重庆@WXN|万州机场|wz|wanzhou|万州@HKG|香港机场|xg|xianggang|香港@MFM|澳门机场|am|aomen|澳门@TSA|台北松山机场|tb|taibei|台北@TNN|台南机场|tn|tainan|台南@TXG|台中机场|tz|taizhong|台中@YIE|阿尔山伊尔施机场|aes|aershan|阿尔山@ZHY|中卫香山机场|zw|zhongwei|中卫@TCG|塔城机场|tc|tacheng|塔城@AAT|阿勒泰机场|alt|aletai|阿勒泰@FYN|富蕴机场|alt|aletai|阿勒泰@KJI|喀纳斯机场|kns|kanasi|喀纳斯@JIQ|黔江武陵山机场|qj|qianjiang|黔江@NBS|长白山机场|zbs|zhangbaishan|长白山@GYU|固原六盘山机场|gy|guyuan|固原@TPE|台湾桃园机场|ty|taoyuan|桃园@YZY|张掖机场|zy|zhangye|张掖";
	private static CityCodeJPUtils instance = null;
	private static List<Map<Integer, String>> list = null;

	private CityCodeJPUtils() {
	}

	public static CityCodeJPUtils getInstance() {
		if (instance == null) {
			init();
			return new CityCodeJPUtils();
		}
		return instance;
	}

	/**
	 * 初始化列表
	 */
	private static void init() {
		list = new ArrayList<Map<Integer, String>>();
		String[] arr = STAT_STR.split("@");
		for (String str : arr) {
			Map<Integer, String> map = new HashMap<Integer, String>();
			String[] arrs = str.split("\\|");
			for (int i = 0; i < arrs.length; i++) {
				map.put(i, arrs[i]);
			}
			list.add(map);
		}
	}

	/**
	 * 根据城市名称模糊查询机场列表
	 * 
	 * @param code
	 * @return
	 */
	public List<Map<Integer, String>> getListByCode(String code) {
		code = code.toLowerCase();
		List<Map<Integer, String>> rtnList = new ArrayList<Map<Integer, String>>();
		for (Map<Integer, String> map : list) {
			if (map.get(0).toString().startsWith(code)
					|| map.get(1).toString().startsWith(code)
					|| map.get(2).toString().startsWith(code)
					|| map.get(3).toString().startsWith(code)
					|| map.get(4).toString().startsWith(code)) {
				rtnList.add(map);
			}
		}
		return rtnList;
	}

	public static void main(String[] args) {
		System.out.println(CityCodeJPUtils.getInstance().getListByCode("bj"));
		// System.out.println(CityUtilsJP.getInstance().getListByCode("北京"));
	}
}
