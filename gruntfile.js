// Gruntfile.js
module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        uglify: {
            options: {
                compress:false,
                banner: '/* KhayCake by TheB1ackSheep, Front-End Developer <%= grunt.template.today("yyyy-mm-dd") %> */\n'
            },
            files: {
                src: 'resources/js/*.js',  // source files mask
                dest: 'web/js/',    // destination folder
                expand: true,    // allow dynamic building
                flatten: true,   // remove all unnecessary nesting
                ext: '.min.js'   // replace .js to .min.js
            }
        },
        less: {
            development: {
                options: {
                    banner: '/* KhayCake by TheB1ackSheep, Front-End Developer <%= grunt.template.today("yyyy-mm-dd") %> */\n',
                    paths: ["web/css"],
                    compress: true
                },
                files: {"web/css/<%= pkg.name %>.min.css": "resources/css/<%= pkg.name %>.less",
                    "web/css/admin.min.css": "resources/admin.less"}
            }
        },
        watch: {
            styles: {
                files: ['resources/js/*.js','resources/css/*.less'], // which files to watch
                tasks: ['uglify','less'],
                options: {
                    nospawn: true
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-less');

};