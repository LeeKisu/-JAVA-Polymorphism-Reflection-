## Polymorphism과 Reflection을 활용 유연한 자바 코드 작성 ##
유연한 코드 작성은 응용 프로그램 개발시 주요하게 고려해야 할 사항 중 하나이다. 프로그램 개발이 완료되어도 유지보수나 변경된 요구사항 적용 등을 위해 프로그램의 코드는 끊임없이 수정되어야 한다. 만일 유연한 프로그램을 만들지 않았다면 하나의 변경사항을 위해 수많은 라인의 코드를 다시 짜야 할 것이다.

간단한 Java프로그램 실습 중, 유연하지 못한 코드를 경험 할 수 있었다. ProductManager을 개발하고 있었는데, 여기에는 각 제품 카테고리 별로 읽기, 입력 패널이 나타난다. 만약 각 카테고리를 위한 패널을 하드 코딩으로 작성한다면, 당장에는 편할지 몰라도, 이후에 새로운 카테고리의 제품이 추가될 때마다 하드코딩을 다시 해야한다. 그래서 Polymorphism과 Reflection을 활용하여 제품 카테고리 추가시에도 유연하게 대응할 수 있는 코드를 고민해보았다.


**1. Polymorphism(다형성)**

먼저 제품 클래스의 기본이 되는 Product 클래스를 만들고 이를 상속받아 제품 카테고리 클래스를 정의하였다.
> public class Product(){}
> 
> public class CPU extends Product{}
> 
> public class GPU extends Product{}
> 
> public class Monitor extends Product{}

그리고 Product형 ArrayList를 같는 ProductManager 클래스를 정의하였다. 이후 ProductManager가 하는 모든 카테고리의 제품들의 동작은 다형성을 활용해 이루어진다.

> ArrayList<Product> list;
>
> public void addProduct(Product p) throws IdExistException{
> 
> ......
>		
> list.add(p);
>	
> .......
>		
> } 

특정 제품 카테고리 클래스로의 동작이 필요한 경우에는 클래스의 타입을 받아서 처리한다.

> public Product searchProduct(int id, Class type) {
> 
> ......
> 
> if(type.isInstance(p))
> 
> .......
> 
> }

**2. Reflection(클래스 반영)**

다형성을 활용해 객체를 처리한다고 해도, 특정 클래스를 위한 GUI나 동작이 필요한 경우가 있다. 이 경우 Reflection을 활용하여 클래스 타입만 넘겨주면 이에 맞는 GUI를 생성하고 동작을 수행하게 하였다.

각 제품 카테고리에 맞는 GUI를 생성하기 위해서 Class와 부모의 필드를 알아낸 후, 필드별로 Label과 TextArea를 생성하였다.

> public ProductInfoPannel(Class type) {
> 
> .......
> 
> ArrayList<String> fileds = getFileds(type);
>
> ......
>
> for(int i = 0; i < fileds.size(); i++) {
>
> ......
>
> }
>
> private ArrayList<String> getFileds(Class type){
>
> ......
>
> for(Field f : type.getDeclaredFields()) {
>
> ......
>
>}

새로운 제품 객체를 생성할 때도 Class 타입에 맞는 생성자를 불러오고 이를 사용하여 객체를 생성하였다.

> public Product addProduct() throws Exception {
>
> ......
> 
> Constructor cons = type.getConstructor(new Class[] {String[].class});
>
> ......
>
> p = (Product) cons.newInstance(new Object[] {values});
>
> ......
>
> }

**3. 결론**

위와같은 기법들을 활용하여 새로운 제품 카테고리가 추가되더라도, 하드코딩 없이 한줄의 코드 추가만으로 수정이 끝난다.

> addCategory(CPU.class);
> 
> addCategory(GPU.class);
> 
> addCategory(Monitor.class);
>
> ......

이 프로젝트를 통해, Polymorphism(다형성)과 Reflection(클래스 반영)에 대해 더욱 이해할 수 있었고, 유연한 코드의 강력함을 느낄 수 있었다.
