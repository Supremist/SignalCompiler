package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;

/**
 * Created by supremist on 4/11/16.
 */
public class Program extends NamedTreeNode {

    public TreeNode parse(TokenIterator iterator) throws ParseException{
        clearChildren();
        TokenNode node = parseTokenNode(iterator);
        parseIdentifier(iterator);
        if(node.getToken().getId() == 401) { //PROCEDURE
            //TODO Parse ParamList, Block
            parseExactTokenNode(iterator, 0); // ;
        }
        else if(node.getToken().getId() == 400) { //PROGRAM
            //TODO Parse Block
            Declaration dec = new Declaration();
            dec.parse(iterator);
            addChild(dec);
            parseExactTokenNode(iterator, 0); // ;
        }
        return this;
    }
}
