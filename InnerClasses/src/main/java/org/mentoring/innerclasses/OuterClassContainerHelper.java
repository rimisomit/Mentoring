package org.mentoring.innerclasses;

/**
 * Created by user on 1/8/15.
 */
public class OuterClassContainerHelper {

    public static void main(String[] args) {
        // Error. Cannot create inner w/o outer
        //OuterClassContainer.InnerMemberPublic innerMemberPublic0 = new OuterClassContainer.InnerMemberPublic();

        //Inner member
        OuterClassContainer outerClassContainer = new OuterClassContainer();
        OuterClassContainer.InnerMemberPublic innerMemberPublic1 = outerClassContainer.new InnerMemberPublic();
        OuterClassContainer.InnerMemberPublic innerMemberPublic2 = new OuterClassContainer().new InnerMemberPublic();

        //Access to fields Inner member
        //Error
        //System.out.println(innerMemberPublic1.a);
        System.out.println(innerMemberPublic1.b);

        //Nested static
        OuterClassContainer.InnerStaticPublic innerStaticPublic = new OuterClassContainer.InnerStaticPublic();


        //Access to fields Nested Static
        System.out.println(innerStaticPublic.a_pub);

    }

    public void createInstance() {
        // Error. Cannot create inner w/o outer
        //OuterClassContainer.InnerMemberPublic innerMemberPublic0 = new OuterClassContainer.InnerMemberPublic();

        //Inner member
        OuterClassContainer outerClassContainer = new OuterClassContainer();
        OuterClassContainer.InnerMemberPublic innerMemberPublic1 = outerClassContainer.new InnerMemberPublic();
        OuterClassContainer.InnerMemberPublic innerMemberPublic2 = new OuterClassContainer().new InnerMemberPublic();

        //Access to fields Inner member
        //Error
        //System.out.println(innerMemberPublic1.a);
        System.out.println(innerMemberPublic1.b);

        //Nested static
        OuterClassContainer.InnerStaticPublic innerStaticPublic = new OuterClassContainer.InnerStaticPublic();


        //Access to fields Nested Static
        System.out.println(innerStaticPublic.a_pub);
        System.out.println(OuterClassContainer.InnerStaticPublic.b_stat);

    }



}
