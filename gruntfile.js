// Gruntfile.js
module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        uglify: {
            options: {
                compress:false,
                banner: '/* KhayCake by TheB1ackSheep, Front-End Developer <%= grunt.template.today("yyyy-mm-dd") %> */\n'
            },
            build: {
                src: 'src/sit/khaycake/js/<%= pkg.name %>.js',
                dest: 'web/js/<%= pkg.name %>.min.js'
            }
        },
        less: {
            development: {
                options: {
                    banner: '/* KhayCake by TheB1ackSheep, Front-End Developer <%= grunt.template.today("yyyy-mm-dd") %> */\n',
                    paths: ["web/css"],
                    compress: true
                },
                files: {"web/css/<%= pkg.name %>.min.css": "src/sit/khaycake/css/<%= pkg.name %>.less"}
            },
            production: {
                options: {
                    banner: '/* KhayCake by TheB1ackSheep, Front-End Developer <%= grunt.template.today("yyyy-mm-dd") %> */\n',
                    paths: ["web/css"],
                    compress: true
                },
                files: {"web/css/<%= pkg.name %>.min.css": "src/sit/khaycake/css/<%= pkg.name %>.less"}
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