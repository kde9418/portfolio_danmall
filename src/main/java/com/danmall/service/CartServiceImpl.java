package com.danmall.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danmall.domain.CartVO;
import com.danmall.domain.CartVOList;
import com.danmall.dto.CartDTO;
import com.danmall.mapper.CartMapper;

import lombok.Setter;

@Service
public class CartServiceImpl implements CartService {

	@Setter(onMethod_ = @Autowired)
	private CartMapper cartMapper;
	
	// 장바구니
	@Override
	public void add_cart(CartVO vo) throws Exception {
		// TODO Auto-generated method stub
		cartMapper.add_cart(vo);
	}

	// 장바구니 목록
	@Override
	public List<CartVOList> list_cart(String mem_id) throws Exception {
		// TODO Auto-generated method stub
		return cartMapper.list_cart(mem_id);
	}

	// 장바구니 전체 비우기
	@Override
	public void delete_cartAll(String mem_id) throws Exception {
		// TODO Auto-generated method stub
		cartMapper.delete_cartAll(mem_id);
	}

	// 장바구니 제품 수량 수정
	@Override
	public void cart_amount_update(long cart_code, int cart_amount) throws Exception {
		// TODO Auto-generated method stub
		cartMapper.cart_amount_update(cart_code, cart_amount);
	}

	// 장바구니 체크 삭제
	@Override
	public void cart_check_delete(List<Integer> checkArr) throws Exception {
		// TODO Auto-generated method stub
		cartMapper.cart_check_delete(checkArr);
	}

	//
	@Override
	public List<CartDTO> cart_money() throws Exception {
		// TODO Auto-generated method stub
		return cartMapper.cart_money();
	}
	//
	@Override
    public JSONObject getChartData() throws Exception  {//제이슨 오브젝트를 리턴하는 것
        // getChartData메소드를 호출하면
        //db에서 리스트 받아오고, 받아온걸로 json형식으로 만들어서 리턴을 해주게 된다.
       List<CartDTO> items = cartMapper.cart_money();
		/*
       List<CartDTO> items = new ArrayList<CartDTO>();
        
        Random rand = new Random();
        
        for(int i=1;i <= 5; i++) {
        	CartDTO cart = new CartDTO();
        	int price = rand.nextInt(10000 - 1000 + 1) + 1000; // 1000 ~ 10000
        	cart.setAmount(price);
        	cart.setPdt_name("전자제품" + i);
        	
        	items.add(cart);
        }
        */
        
        //리턴할 json 객체. HashMap 클래스 상속
        JSONObject data = new JSONObject(); //{}
        
        //json의 칼럼 객체
        JSONObject col1 = new JSONObject();
        JSONObject col2 = new JSONObject();
        
        //json 배열 객체, 배열에 저장할때는 JSONArray()를 사용.ArrayList 클래스 상속
        JSONArray title = new JSONArray();
        col1.put("label","상품명"); //col1에 자료를 저장 ("필드이름","자료형")
        col1.put("type", "string");
        col2.put("label", "금액");
        col2.put("type", "number");
        
        //테이블행에 컬럼 추가
        title.add(col1);
        title.add(col2);
        
        //json 객체에 타이틀행 추가
        data.put("cols", title);//제이슨을 넘김
        //이런형식으로 추가가된다. {"cols" : [{"label" : "상품명","type":"string"}
        //,{"label" : "금액", "type" : "number"}]}
        
        JSONArray body = new JSONArray(); //json 배열을 사용하기 위해 객체를 생성
        for (CartDTO dto : items) { //items에 저장된 값을 dto로 반복문을 돌려서 하나씩 저장한다.
            
            JSONObject name = new JSONObject(); //json오브젝트 객체를 생성
            name.put("v", dto.getPdt_name()); //name변수에 dto에 저장된 상품의 이름을 v라고 저장한다.
            
            JSONObject money = new JSONObject(); //json오브젝트 객체를 생성
            money.put("v", dto.getAmount()); //name변수에 dto에 저장된 금액을 v라고 저장한다.
            
            JSONArray row = new JSONArray(); //json 배열 객체 생성 (위에서 저장한 변수를 칼럼에 저장하기위해)
            row.add(name); //name을 row에 저장 (테이블의 행)
            row.add(money); //name을 row에 저장 (테이블의 행)
            
            JSONObject cell = new JSONObject(); 
            cell.put("c", row); //cell 2개를 합쳐서 "c"라는 이름으로 추가
            body.add(cell); //레코드 1개 추가
                
        }
        data.put("rows", body); //data에 body를 저장하고 이름을 rows라고 한다.
        
        return data; //이 데이터가 넘어가면 json형식으로 넘어가게되서 json이 만들어지게 된다.
    }

	// 장바구니 체크 구매(선택된 것을 제외한 상품삭제)
	@Override
	public void cart_not_check_delete(List<Integer> checkArr) throws Exception {
		cartMapper.cart_not_check_delete(checkArr);
	}

}
