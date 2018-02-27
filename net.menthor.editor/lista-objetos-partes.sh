#more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//' | sed 's/ <<.*>>//g' > lista-completa.txt
#more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//' > lista-completa.txt
kind=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<Kind>>'|wc -l)
echo Kind $kind

subkind=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<SubKind>>'|wc -l)
echo SubKind $subkind

role=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<Role>>'|wc -l)
echo Role $role

phase=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<Phase>>'|wc -l)
echo Phase $phase

relator=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<Relator>>'|wc -l)
echo Relator $relator

category=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<Category>>'|wc -l)
echo Category $category

collective=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<Collective>>'|wc -l)
echo Collective $collective

mixin=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<Mixin>>'|wc -l)
echo Mixin $mixin

mode=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<Mode>>'|wc -l)
echo Mode $mode

quantity=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<Quantity>>'|wc -l)
echo Quantity $quantity

rolemixin=$(more plantuml-*Pattern.txt | grep class | grep "<<" | sort -u | sed 's/class//'| grep '<<RoleMixin>>'|wc -l)
echo RoleMixin $rolemixin
