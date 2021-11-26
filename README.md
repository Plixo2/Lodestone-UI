# Lodestone-UI

## object oriented ui system 

-  for all different backends like java graphics or opengl
-  can reflect any class in the ui
-  supports with two way binding
-  supports all easings.net animations

```java
//reflect an object in a scrollable list with an Scrollbar

UIColumn column = new UIColumn();
 
List<FieldReference<?>> fieldReferences = new ReflectionHelper().reflectFields(objectReference);

fieldReferences.forEach(ref -> {
   UIReference reference = new UIReference();
   reference.initialize(0,0,width,height,ref.getName(),ref,ref.getField().getType());
   column.add(reference);
});

column.pack();

UIScrollBar scrollBar = new UIScrollBar();
scrollBar.setDimensions(0,0,10,200);
scrollBar.setReturnPercent(column::setPercent);
scrollBar.setSupplier(column::getPercent);

//animation
Animation.animate(column::setPercent,0,1,5, Ease.InOutCubic);

```
