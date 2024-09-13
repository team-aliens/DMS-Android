# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# keep everything in this package from being removed or renamed
-keep class team.** { *; }

# keep everything in this package from being renamed only
-keepnames class team.** { *; }
# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class okhttp3.** { *; }

# keep everything in this package from being renamed only
-keepnames class okhttp3.** { *; }
# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class kotlin.** { *; }

# keep everything in this package from being renamed only
-keepnames class kotlin.** { *; }
# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class okio.** { *; }

# keep everything in this package from being renamed only
-keepnames class okio.** { *; }
# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class java.** { *; }

# keep everything in this package from being renamed only
-keepnames class java.** { *; }
# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class retrofit2.** { *; }

# keep everything in this package from being renamed only
-keepnames class retrofit2.** { *; }
# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class org.** { *; }

# keep everything in this package from being renamed only
-keepnames class org.** { *; }
# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class javax.** { *; }

# keep everything in this package from being renamed only
-keepnames class javax.** { *; }
# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @androidx.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class _COROUTINE.** { *; }

# keep everything in this package from being renamed only
-keepnames class _COROUTINE.** { *; }

-dontwarn com.google.devtools.ksp.processing.SymbolProcessorProvider
