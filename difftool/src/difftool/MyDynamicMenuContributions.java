//package difftool;
//
//import org.eclipse.ui.menus.CommandContributionItem;
//import org.eclipse.ui.menus.CommandContributionItemParameter;
//import org.eclipse.ui.menus.ExtensionContributionFactory;
//import org.eclipse.ui.menus.IContributionRoot;
//import org.eclipse.ui.services.IServiceLocator;
//
//public class MyDynamicMenuContributions extends ExtensionContributionFactory {
//
//	@Override
//	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {
//		 // build a couple of command-based contribution parameters
//        CommandContributionItemParameter pAA = new CommandContributionItemParameter(
//            serviceLocator,
//            "difftool.menus.sampleCommand1",
//            "difftool.commands.sampleCommand1",
//            1);
//
//
//        // create actual contribution items and add them to the given additions reference
//        
//        CommandContributionItem itemAA = new CommandContributionItem(pAA);
//        itemAA.setVisible(true);
//        
//        additions.addContributionItem(itemAA, null);
//
//    }
//}
