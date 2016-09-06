# MDR Model Implementation for ArgoUML

## Checkout MDR sources

Usually this should have worked :
```
$ export CVSROOT=:pserver:anoncvs@cvs.netbeans.org:/cvs
$ cvs checkout -D 20060101 mdr
```

## Old Jars

If you cannot find the sources, use something like `jadx` to have a source 
folder,  however in my case it didn't have the right line numbers.

## Current errors with XmiResolverImpl (patched)

```
$ grep -r "<<< FAILURE" src/argouml-app/target/surefire-reports/*.txt | grep run
src/argouml-app/target/surefire-reports/org.argouml.TestDependencies.txt:Tests run: 38, Failures: 2, Errors: 0, Skipped: 0, Time elapsed: 3.247 sec <<< FAILURE!
src/argouml-app/target/surefire-reports/org.argouml.persistence.TestZargoFilePersister.txt:Tests run: 8, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 17.277 sec <<< FAILURE!
```

## Current errors with XmiResolverImpl2

```
$ grep -r "<<< FAILURE" target/surefire-reports/*.txt | grep run
target/surefire-reports/org.argouml.TestDependencies.txt:Tests run: 38, Failures: 2, Errors: 0, Skipped: 0, Time elapsed: 3.851 sec <<< FAILURE!
target/surefire-reports/org.argouml.kernel.TestProject.txt:Tests run: 14, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 5.882 sec <<< FAILURE!
target/surefire-reports/org.argouml.kernel.TestProjectWithProfiles.txt:Tests run: 4, Failures: 0, Errors: 3, Skipped: 0, Time elapsed: 6.632 sec <<< FAILURE!
target/surefire-reports/org.argouml.persistence.TestXmiFilePersister.txt:Tests run: 5, Failures: 0, Errors: 4, Skipped: 0, Time elapsed: 7.127 sec <<< FAILURE!
target/surefire-reports/org.argouml.persistence.TestZargoFilePersister.txt:Tests run: 8, Failures: 0, Errors: 7, Skipped: 0, Time elapsed: 11.33 sec <<< FAILURE!
target/surefire-reports/org.argouml.profile.TestProfileMother.txt:Tests run: 7, Failures: 3, Errors: 0, Skipped: 0, Time elapsed: 4.645 sec <<< FAILURE!
target/surefire-reports/org.argouml.ui.TestPropertyPanels.txt:Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 4.923 sec <<< FAILURE!
target/surefire-reports/org.argouml.uml.ui.foundation.extension_mechanisms.TestUMLTagDefinitionComboBoxModel.txt:Tests run: 2, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 7.766 sec <<< FAILURE!
```
