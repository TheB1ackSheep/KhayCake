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
                src: 'src/sit/khaycake/js/*.js',  // source files mask
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
                files: {"web/css/<%= pkg.name %>.min.css": "src/sit/khaycake/css/<%= pkg.name %>.less",
                    "web/css/admin.min.css": "src/sit/khaycake/css/admin.less"}
            }
        },
        watch: {
            styles: {
                files: ['src/sit/khaycake/js/*.js','src/sit/khaycake/css/*.less'], // which files to watch
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